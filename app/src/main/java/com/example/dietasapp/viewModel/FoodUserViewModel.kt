package com.example.dietasapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietasapp.data.Utils
import com.example.dietasapp.data.model.FoodModel
import com.example.dietasapp.data.model.FoodUserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FoodUserViewModel: ViewModel() {

    private var listFoodUser = MutableLiveData<MutableList<FoodUserModel>>()
    private lateinit var dietId: String
    private lateinit var mealId: String
    private var food = MutableLiveData<FoodModel>()

    fun getListFoodUser(): LiveData<MutableList<FoodUserModel>> {
        return listFoodUser
    }

    fun getFood(): LiveData<FoodModel> {
        return food
    }

    /**
     * Set the current diet ID.
     * @param id The ID of the current diet.
     */
    fun setDietId(id: String) {
        dietId = id
    }

    /**
     * Set the current meal ID.
     * @param id The ID of the current meal.
     */
    fun setMealId(id: String) {
        mealId = id
    }

    /**
     * Sets the value of the LiveData representing a selected food item.
     *
     * This function updates the value of the LiveData with the provided [FoodModel].
     *
     * @param f The [FoodModel] to be set as the selected food item.
     */
    fun setFood(f: FoodModel) {
        food.value = f
    }

    /**
     * Update the list of food users from Firestore.
     */
    fun updateAllFoodUserDB() {
        viewModelScope.launch {
            try {
                val colFoodUserRef = Utils.Firestore.getUserFoodUsersColRef(dietId, mealId)

                // Use withContext para mudar para o contexto Dispatchers.IO para operações de rede
                val querySnapshot = withContext(Dispatchers.IO) {
                    colFoodUserRef.get().await()
                }

                val list = mutableListOf<FoodUserModel>()

                for (doc in querySnapshot) {
                    val foodUser = doc.toObject(FoodUserModel::class.java)
                    foodUser.id = doc.id
                    foodUser.dietId = dietId

                    val docFood = withContext(Dispatchers.IO) {
                        Utils.Firestore.getDocRef(foodUser.foodReference).get().await()
                    }

                    val food = docFood.toObject(FoodModel::class.java)
                    if (food != null) {
                        Log.i("FoodUserViewModelUpdate", "Alimento: $food")
                        food.id = docFood.id
                        foodUser.food = food
                    }

                    list.add(foodUser)
                    Log.i("FoodUserViewModelUpdate", "Refeição: $foodUser")
                }

                listFoodUser.value = list
            } catch (e: Exception) {
                // Lide com exceções, por exemplo, imprima o erro
                e.printStackTrace()
                Log.e("FoodUserViewModel", "Erro ao buscar refeições")
            }
        }
    }

    /**
     * Create a new food user in Firestore.
     * @param foodUser The food user to be created.
     */
    fun createFoodUser(foodUser: FoodUserModel) {
        val colMFoodUserRef = Utils.Firestore.getUserFoodUsersColRef(dietId, mealId)
        colMFoodUserRef.add(foodUser)
            .addOnSuccessListener {
                Log.i("FoodUserViewModel", "Food user created successfully")
                updateAllFoodUserDB()
            }
            .addOnFailureListener {
                Log.e("FoodUserViewModel", "Failed to create food user")
            }
    }

    /**
     * Update an existing food user in Firestore.
     * @param foodUser The updated food user information.
     */
    fun updateFoodUser(foodUser: FoodUserModel) {
        val docFoodUserRef = Utils.Firestore.getUserFoodUserDocRef(dietId, mealId, foodUser.id)
        docFoodUserRef.update("title", foodUser.food.title, "description", foodUser.description, "grams", foodUser.grams)
            .addOnSuccessListener {
                Log.i("FoodUserViewModel", "Food user updated successfully")
                updateAllFoodUserDB()
            }
            .addOnFailureListener {
                Log.e("FoodUserViewModel", "Failed to update food user")
            }
    }

    /**
     * Delete a food user from Firestore.
     * @param fu The food user to be deleted.
     */
    fun deleteFoodUser(fu: FoodUserModel) {
        val docFoodUserRef = Utils.Firestore.getUserFoodUserDocRef(dietId, mealId, fu.id)
        docFoodUserRef.delete()
            .addOnSuccessListener {
                Log.i("FoodUserViewModel", "Food user deleted successfully")
                updateAllFoodUserDB()
            }
            .addOnFailureListener {
                Log.e("FoodUserViewModel", "Failed to delete food user")
            }
    }

    /**
     * Gets the current meal ID.
     *
     * @return A [String] representing the current meal ID.
     */
    fun getMealId(): String {
        return mealId
    }

    /**
     * Gets the current diet ID.
     *
     * @return A [String] representing the current diet ID.
     */
    fun getDietId(): String {
        return dietId
    }

}
