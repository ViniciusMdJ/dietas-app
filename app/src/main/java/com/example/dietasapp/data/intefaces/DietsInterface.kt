package com.example.dietasapp.data.intefaces

import com.example.dietasapp.data.model.DietModel
import com.example.dietasapp.data.model.MealModel
import com.example.dietasapp.databinding.DietLineBinding

interface DietsInterface {
    fun setDietsClickListener(d: DietModel, binding: DietLineBinding)
}