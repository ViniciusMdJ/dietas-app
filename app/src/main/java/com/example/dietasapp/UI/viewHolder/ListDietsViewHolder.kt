package com.example.dietasapp.UI.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.dietasapp.data.model.DietModel
import com.example.dietasapp.databinding.DietLineBinding

class ListDietsViewHolder(private val binding: DietLineBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindVH(diet: DietModel){
        binding.title.text = diet.title
        binding.descText.text = diet.description
        binding.calText.text = diet.calorie.toString()
        binding.carbText.text = diet.carbohydrate.toString()
        binding.proteinText.text = diet.protein.toString()
        binding.fatText.text = diet.fat.toString()
    }

}
