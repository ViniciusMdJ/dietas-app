package com.example.dietasapp.UI.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.dietasapp.data.model.FoodsUserModel
import com.example.dietasapp.databinding.FoodLineBinding

class ListFoodsViewHolder(private val binding: FoodLineBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bindVH(food: FoodsUserModel){
        binding.foodTitleText.text = food.title
        binding.foodDescriptionText.text = food.description
        binding.calText.text = food.calorie.toString()
        binding.carbText.text = food.carbohydrate.toString()
        binding.proteinText.text = food.protein.toString()
        binding.fatText.text = food.fat.toString()
    }

}