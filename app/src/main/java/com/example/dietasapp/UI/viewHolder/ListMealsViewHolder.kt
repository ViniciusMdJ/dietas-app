package com.example.dietasapp.UI.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.dietasapp.data.model.MealModel
import com.example.dietasapp.databinding.MealLineBinding
import java.text.SimpleDateFormat

/**
 * View holder for displaying meal information in a RecyclerView.
 *
 * This class is responsible for binding meal data to the corresponding views within the layout.
 *
 * @param binding The view binding representing the layout of a single meal item.
 */
class ListMealsViewHolder(private val binding: MealLineBinding) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Binds meal data to the views within the layout.
     *
     * @param meal The [MealModel] containing information about the meal to be displayed.
     */
    fun bindVH(meal: MealModel) {
        // Set the title, nutritional information, and date for the meal
        binding.mealTitle.text = meal.title
        binding.calText.text = "${meal.calorie} kcal"
        binding.carbText.text = "${meal.carbohydrate} g"
        binding.proteinText.text = "${meal.protein} g"
        binding.fatText.text = "${meal.fat} g"

        // Format and set the date using SimpleDateFormat
        val sdf = SimpleDateFormat("HH:mm")
        binding.clockText.text = sdf.format(meal.date)
    }
}