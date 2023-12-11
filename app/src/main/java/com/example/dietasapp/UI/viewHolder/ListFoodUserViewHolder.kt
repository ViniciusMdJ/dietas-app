package com.example.dietasapp.UI.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.dietasapp.data.model.FoodUserModel
import com.example.dietasapp.databinding.FoodLineBinding

/**
 * View holder for displaying user-specific food information in a RecyclerView.
 *
 * This class is responsible for binding user-specific food data to the corresponding views within the layout.
 *
 * @param binding The view binding representing the layout of a single user-specific food item.
 */
class ListFoodUserViewHolder(private val binding: FoodLineBinding) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Binds user-specific food data to the views within the layout.
     *
     * @param food The [FoodUserModel] containing information about the user-specific food to be displayed.
     */
    fun bindVH(food: FoodUserModel) {
        // Set the title, description, and nutritional information for the user-specific food
        binding.foodTitleText.text = food.food.title
        binding.foodDescriptionText.text = food.description
        binding.calText.text = "${food.food.calorie} kcal"
        binding.carbText.text = "${food.food.carbohydrate} g"
        binding.proteinText.text = "${food.food.protein} g"
        binding.fatText.text = "${food.food.fat} g"
    }
}