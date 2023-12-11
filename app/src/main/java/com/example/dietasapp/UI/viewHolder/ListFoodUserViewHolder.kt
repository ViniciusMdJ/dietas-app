package com.example.dietasapp.UI.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.dietasapp.data.model.FoodUserModel
import com.example.dietasapp.databinding.FoodLineBinding

class ListFoodUserViewHolder(private val binding: FoodLineBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bindVH(food: FoodUserModel){
        binding.foodTitleText.text = food.title
        binding.foodDescriptionText.text = food.description
        binding.calText.text = food.calorie.toString() + " kcal"
        binding.carbText.text = food.carbohydrate.toString() + " g"
        binding.proteinText.text = food.protein.toString() + " g"
        binding.fatText.text = food.fat.toString() + " g"
    }

}