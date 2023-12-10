package com.example.dietasapp.data.intefaces

import com.example.dietasapp.data.model.MealModel
import com.example.dietasapp.databinding.MealLineBinding

interface MealsInterface {
    fun setMealsClickListener(m: MealModel, binding: MealLineBinding)
}