package com.example.dietasapp.UI.viewHolder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.dietasapp.data.model.FoodModel
import com.example.dietasapp.databinding.FoodSearchLineBinding

/**
 * View holder for displaying food information in a search list RecyclerView.
 *
 * This class is responsible for binding food data to the corresponding views within the layout.
 *
 * @param binding The view binding representing the layout of a single food item in the search list.
 */
class ListFoodViewHolder(private val binding: FoodSearchLineBinding) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Binds food data to the views within the layout.
     *
     * @param f The [FoodModel] containing information about the food to be displayed.
     */
    @SuppressLint("SetTextI18n")
    fun bindVH(f: FoodModel) {
        binding.nameFoodSearch.text = f.title
        binding.calText.text = "${f.calorie} kcal"
        binding.carbText.text = "${f.carbohydrate} g"
        binding.proteinText.text = "${f.protein} g"
        binding.fatText.text = "${f.fat} g"
    }
}