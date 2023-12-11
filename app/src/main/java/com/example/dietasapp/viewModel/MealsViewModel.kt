package com.example.dietasapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietasapp.data.Utils
import com.example.dietasapp.data.model.MealModel

/**
 * ViewModel for managing meal-related data.
 */
class MealsViewModel : ViewModel() {
    private var listMeals = MutableLiveData<MutableList<MealModel>>()
    private lateinit var dietId: String

    /**
     * Set the ID of the associated diet.
     * @param id The ID of the diet.
     */
    fun setDietId(id: String) {
        dietId = id
    }

    /**
     * Get LiveData for the list of meals.
     * @return LiveData containing the list of meals.
     */
    fun getListMeals(): LiveData<MutableList<MealModel>> {
        return listMeals
    }

    /**
     * Update the list of meals from the Firestore database.
     */
    fun updateAllMealsDB(){
        val colMealRef = Utils.Firestore.getUserMealsColRef(dietId)

        colMealRef.get().addOnSuccessListener {
            val list = mutableListOf<MealModel>()
            for (doc in it){
                val meal = doc.toObject(MealModel::class.java)
                meal.id = doc.id
                meal.dietId = dietId
                Log.i("MealsViewModelUpdate", "Meal: $meal")
                list.add(meal)
            }
            listMeals.value = list
        }
            .addOnFailureListener {
                Log.e("MealsViewModel", "Error fetching meals")
            }
    }

    /**
     * Create a new meal and add it to the Firestore database.
     * @param meal The meal to be created.
     */
    fun createMeals(meal: MealModel){
        val colMealRef = Utils.Firestore.getUserMealsColRef(dietId)
        colMealRef.add(meal)
            .addOnSuccessListener {
                Log.i("MealsViewModel", "Meal created successfully")
                updateAllMealsDB()
            }
            .addOnFailureListener {
                Log.e("MealsViewModel", "Error creating meal")
            }
    }

    /**
     * Update an existing meal in the Firestore database.
     * @param meal The updated meal.
     */
    fun updateMeals(meal: MealModel){
        Log.i("MealsViewModel", "Meal: $meal")
        val docMealRef = Utils.Firestore.getUserMealsDocRef(meal.dietId, meal.id)
        docMealRef.update("title", meal.title, "date", meal.date)
            .addOnSuccessListener {
                Log.i("MealsViewModel", "Meal updated successfully")
                updateAllMealsDB()
            }
            .addOnFailureListener {
                Log.e("MealsViewModel", "Error updating meal")
            }
    }

    /**
     * Delete a meal from the Firestore database.
     * @param m The meal to be deleted.
     */
    fun deleteMeal(m: MealModel) {
        val docMealRef = Utils.Firestore.getUserMealsDocRef(m.dietId, m.id)
        docMealRef.delete()
            .addOnSuccessListener {
                Log.i("MealsViewModel", "Meal deleted successfully")
                updateAllMealsDB()
            }
            .addOnFailureListener {
                Log.e("MealsViewModel", "Error deleting meal")
            }
    }
}