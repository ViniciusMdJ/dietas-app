package com.example.dietasapp.UI.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.dietasapp.data.model.MealModel
import com.example.dietasapp.databinding.MealLineBinding
import java.text.SimpleDateFormat

class ListMealsViewHolder(private val binding: MealLineBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindVH(meal: MealModel){
        binding.mealTitle.text = meal.title
        binding.calText.text = meal.calorie.toString()
        binding.carbText.text = meal.carbohydrate.toString()
        binding.proteinText.text = meal.protein.toString()
        binding.fatText.text = meal.fat.toString()
        binding.clockText.text = meal.date.toString()
        val sdf = SimpleDateFormat("HH:mm")
        binding.clockText.text = sdf.format(meal.date)
    }

}