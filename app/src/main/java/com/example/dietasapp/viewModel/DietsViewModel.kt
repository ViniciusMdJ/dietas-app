package com.example.dietasapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietasapp.data.Utils
import com.example.dietasapp.data.model.DietModel
import com.example.dietasapp.data.model.FoodModel
import com.example.dietasapp.data.model.UserModel
import com.google.firebase.firestore.FieldValue

class DietsViewModel: ViewModel() {

    private var listDiets = MutableLiveData<MutableList<DietModel>>()
    private var user = MutableLiveData<UserModel>()
    private var favoriteDiet = MutableLiveData<String>()

    /**
     * Initialization block to set default values and fetch user data from Firestore.
     */
    init {
        listDiets.value = mutableListOf()
        getUserFirestore()
    }

    /**
     * Get LiveData for the list of diets.
     * @return LiveData containing the list of diets.
     */
    fun getListDiets(): LiveData<MutableList<DietModel>> {
        return listDiets
    }

    /**
     * Get LiveData for the user data.
     * @return LiveData containing the user.
     */
    fun getUser(): LiveData<UserModel> {
        return user
    }

    /**
     * Get LiveData for the favorite diet ID.
     * @return LiveData containing the favorite diet ID.
     */
    fun getFavoriteDiet(): LiveData<String> {
        return favoriteDiet
    }

    /**
     * Set the favorite diet and update it in Firestore.
     * @param dietId The ID of the diet to be set as the favorite.
     */
    fun setFavoriteDiet(dietId: String){
        updateFavoriteDiet(dietId)
        favoriteDiet.value = dietId
    }

    /**
     * Update the favorite diet ID in Firestore.
     * @param dietId The ID of the diet to be set as the favorite.
     */
    private fun updateFavoriteDiet(dietId: String) {
        val docUserRef = Utils.Firestore.getUserDocRef()
        docUserRef.update("dietaFavorita", dietId)
            .addOnSuccessListener {
                Log.i("DietsViewModel", "Favorite diet updated successfully")
            }
            .addOnFailureListener {
                Log.e("DietsViewModel", "Failed to update favorite diet")
            }
    }

    /**
     * Fetch user data from Firestore.
     */
    private fun getUserFirestore() {
        Utils.Firestore.getUserData().addOnSuccessListener {
            val user = it.toObject(UserModel::class.java)
            if (user != null) {
                user.email = Utils.Firestore.getUserEmail()!!
                this.user.value = user
                favoriteDiet.value = user.dietaFavorita
            }
        }
    }

    /**
     * Update the list of diets from Firestore.
     */
    fun updateAllDietsDB() {
        val colDietRef = Utils.Firestore.getUserDietsColRef()

        colDietRef.get().addOnSuccessListener {
            val list = mutableListOf<DietModel>()
            for (doc in it) {
                val diet = doc.toObject(DietModel::class.java)
                diet.id = doc.id
                list.add(diet)
            }
            listDiets.value = list
        }
            .addOnFailureListener {
                Log.e("DietsViewModel", "Failed to fetch diets")
            }
    }

    /**
     * Create a new diet in Firestore.
     * @param diet The diet to be created.
     */
    fun createDiet(diet: DietModel) {
        val colDietRef = Utils.Firestore.getUserDietsColRef()
        colDietRef.add(diet)
            .addOnSuccessListener {
                Log.i("DietsViewModel", "Diet created successfully")
                updateAllDietsDB()
            }
            .addOnFailureListener {
                Log.e("DietsViewModel", "Failed to create diet")
            }
    }

    /**
     * Delete a diet from Firestore.
     * @param dietId The ID of the diet to be deleted.
     */
    fun deleteDiet(dietId: String) {
        val colDietRef = Utils.Firestore.getUserDietsColRef()
        colDietRef.document(dietId).delete()
            .addOnSuccessListener {
                Log.i("DietsViewModel", "Diet deleted successfully")
                updateAllDietsDB()
            }
            .addOnFailureListener {
                Log.e("DietsViewModel", "Failed to delete diet")
            }
    }

    /**
     * Update an existing diet in Firestore.
     * @param diet The updated diet information.
     */
    fun updateDiet(diet: DietModel) {
        val colDietRef = Utils.Firestore.getUserDietsColRef()
        colDietRef.document(diet.id)
            .update("title", diet.title, "description", diet.description)
            .addOnSuccessListener {
                Log.i("DietsViewModel", "Diet updated successfully")
                updateAllDietsDB()
            }
            .addOnFailureListener {
                Log.e("DietsViewModel", "Failed to update diet")
            }
    }

    fun updatemacronutrients(dietId: String, food: FoodModel){
        val colDietRef = Utils.Firestore.getUserDietsColRef()
        colDietRef.document(dietId)
            .update("carbohydrate", FieldValue.increment(food.carbohydrate.toLong()),
                "protein", FieldValue.increment(food.protein.toLong()),
                "fat", FieldValue.increment(food.fat.toLong()),
                "calorie", FieldValue.increment(food.calorie.toLong())
            )
            .addOnSuccessListener {
                Log.i("DietsViewModel", "Macronutrientes atualizados com sucesso")
                updateAllDietsDB()
            }
            .addOnFailureListener {
                Log.e("DietsViewModel", "Erro ao atualizar macronutrientes")
            }
    }

    fun updatemacronutrients(dietId: String, oldFood: FoodModel, newFood: FoodModel) {
        val food = FoodModel(
            calorie = newFood.calorie - oldFood.calorie,
            fat = newFood.fat - oldFood.fat,
            protein = newFood.protein - oldFood.protein,
            carbohydrate = newFood.carbohydrate - oldFood.carbohydrate
        )
        updatemacronutrients(dietId, food)
    }
}