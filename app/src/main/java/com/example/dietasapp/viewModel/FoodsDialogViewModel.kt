package com.example.dietasapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietasapp.data.Utils
import com.example.dietasapp.data.model.FoodModel

/**
 * ViewModel for managing data related to food search functionality.
 *
 * This ViewModel is responsible for handling and providing data related to searching for food items.
 */
class FoodsDialogViewModel: ViewModel() {
    // LiveData to observe changes in the list of food items
    private var listFoods = MutableLiveData<MutableList<FoodModel>>()

    /**
     * Gets the LiveData representing the list of food items.
     *
     * @return A LiveData object containing the list of food items.
     */
    fun getListFoods(): LiveData<MutableList<FoodModel>> {
        return listFoods
    }

    /**
     * Searches for food items based on the provided search query.
     *
     * This function queries the Firestore collection for food items with a title matching the given [search] query.
     * The results are then added to the list of food items, and the LiveData is updated with the new list.
     *
     * @param search The search query for finding food items by title.
     */
    fun searchFood(search: String) {
        val list = mutableListOf<FoodModel>()

        val colFoodRef = Utils.Firestore.getFoodColRef().whereEqualTo("title", search)
        colFoodRef.get().addOnSuccessListener { 
            for (doc in it.documents) {
                val food = doc.toObject(FoodModel::class.java)
                if (food != null) {
                    food.id = doc.id
                    list.add(food)
                }
            }
            listFoods.value = list
        }
    }
}