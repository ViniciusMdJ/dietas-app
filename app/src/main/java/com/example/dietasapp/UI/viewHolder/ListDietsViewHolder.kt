package com.example.dietasapp.UI.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.dietasapp.data.model.DietModel
import com.example.dietasapp.databinding.DietLineBinding

/**
 * View holder for displaying diet information in a RecyclerView.
 *
 * This class is responsible for binding diet data to the corresponding views within the layout.
 *
 * @param binding The view binding representing the layout of a single diet item.
 */
class ListDietsViewHolder(private val binding: DietLineBinding) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Binds diet data to the views within the layout.
     *
     * @param diet The [DietModel] containing information about the diet to be displayed.
     */
    fun bindVH(diet: DietModel) {
        // Set the title, description, and nutritional information for the diet
        binding.title.text = diet.title
        binding.descText.text = diet.description
        binding.calText.text = "${diet.calorie} kcal"
        binding.carbText.text = "${diet.carbohydrate} g"
        binding.proteinText.text = "${diet.protein} g"
        binding.fatText.text = "${diet.fat} g"
    }
}
