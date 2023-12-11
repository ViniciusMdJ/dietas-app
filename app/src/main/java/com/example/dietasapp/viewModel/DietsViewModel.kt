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

    init {
        listDiets.value = mutableListOf()
        getUserFirestore()
    }

    fun getListDiets(): LiveData<MutableList<DietModel>>{
        return listDiets
    }

    fun getUser(): LiveData<UserModel> {
        return user
    }

    fun getFavoriteDiet(): LiveData<String> {
        return favoriteDiet
    }

    fun setFavoriteDiet(dietId: String){
        updateFavoriteDiet(dietId)
        favoriteDiet.value = dietId
    }

    fun updateFavoriteDiet(dietId: String){
        val docUserRef = Utils.Firestore.getUserDocRef()
        docUserRef.update("dietaFavorita", dietId)
            .addOnSuccessListener {
                Log.i("DietsViewModel", "Dieta favorita atualizada com sucesso")
            }
            .addOnFailureListener {
                Log.e("DietsViewModel", "Erro ao atualizar dieta favorita")
            }
    }

    private fun getUserFirestore(){
        Utils.Firestore.getUserData().addOnSuccessListener {
            val user = it.toObject(UserModel::class.java)
            if (user != null) {
                user.email = Utils.Firestore.getUserEmail()!!
                this.user.value = user
                favoriteDiet.value = user.dietaFavorita
            }
        }
    }

    fun updateAllDietsDB(){
        val colDietRef = Utils.Firestore.getUserDietsColRef()

        colDietRef.get().addOnSuccessListener {
            val list = mutableListOf<DietModel>()
            for (doc in it){
                val diet = doc.toObject(DietModel::class.java)
                diet.id = doc.id
                list.add(diet)
            }
            listDiets.value = list
        }
            .addOnFailureListener {
                Log.e("DietsViewModel", "Erro ao buscar dietas")
            }
    }

    fun createDiet(diet: DietModel){
        val colDietRef = Utils.Firestore.getUserDietsColRef()
        colDietRef.add(diet)
            .addOnSuccessListener {
                Log.i("DietsViewModel", "Dieta criada com sucesso")
                updateAllDietsDB()
            }
            .addOnFailureListener {
                Log.e("DietsViewModel", "Erro ao criar dieta")
            }
    }

    fun deleteDiet(dietId: String){
        val colDietRef = Utils.Firestore.getUserDietsColRef()
        colDietRef.document(dietId).delete()
            .addOnSuccessListener {
                Log.i("DietsViewModel", "Dieta deletada com sucesso")
                updateAllDietsDB()
            }
            .addOnFailureListener {
                Log.e("DietsViewModel", "Erro ao deletar dieta")
            }
   }

    fun updateDiet(diet: DietModel){
        val colDietRef = Utils.Firestore.getUserDietsColRef()
        colDietRef.document(diet.id)
            .update("title", diet.title, "description", diet.description)
            .addOnSuccessListener {
                Log.i("DietsViewModel", "Dieta atualizada com sucesso")
                updateAllDietsDB()
            }
            .addOnFailureListener {
                Log.e("DietsViewModel", "Erro ao atualizar dieta")
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