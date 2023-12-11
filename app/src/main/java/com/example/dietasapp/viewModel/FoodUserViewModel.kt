package com.example.dietasapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietasapp.data.Utils
import com.example.dietasapp.data.model.FoodUserModel
import com.example.dietasapp.data.model.MealModel

/**
 * ViewModel for managing food user-related data.
 */
class FoodUserViewModel: ViewModel() {

    private var listFoodUser = MutableLiveData<MutableList<FoodUserModel>>()
    private lateinit var dietId: String
    private lateinit var mealId: String

    /**
     * Get LiveData for the list of food users.
     * @return LiveData containing the list of food users.
     */
    fun getListFoodUser(): LiveData<MutableList<FoodUserModel>> {
        return listFoodUser
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
     * Update the list of food users from Firestore.
     */
    fun updateAllFoodUserDB() {
        val colFoodUserRef = Utils.Firestore.getUserFoodUsersColRef(dietId, mealId)

        colFoodUserRef.get().addOnSuccessListener {
            val list = mutableListOf<FoodUserModel>()
            for (doc in it) {
                val foodUser = doc.toObject(FoodUserModel::class.java)
                foodUser.id = doc.id
                foodUser.dietId = dietId
                Log.i("FoodUserViewModelUpdate", "Food user: $foodUser")
                list.add(foodUser)
            }
            listFoodUser.value = list
        }
            .addOnFailureListener {
                Log.e("FoodUserViewModel", "Failed to fetch food users")
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
        docFoodUserRef.update(
            "title", foodUser.title,
            "description", foodUser.description,
            "grams", foodUser.grams
        )
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
}
