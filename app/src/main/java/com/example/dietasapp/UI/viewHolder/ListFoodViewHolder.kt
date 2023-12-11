package com.example.dietasapp.UI.viewHolder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.dietasapp.data.model.FoodModel
import com.example.dietasapp.databinding.FoodSearchLineBinding

class ListFoodViewHolder(private val binding: FoodSearchLineBinding) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bindVH(f: FoodModel){
        binding.nameFoodSearch.text = f.title
        binding.calText.text = "${f.calorie} kcal"
        binding.carbText.text = "${f.carbohydrate} g"
        binding.proteinText.text = "${f.protein} g"
        binding.fatText.text = "${f.fat} g"
    }
}