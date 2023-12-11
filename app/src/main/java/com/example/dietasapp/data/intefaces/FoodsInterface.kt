package com.example.dietasapp.data.intefaces

import com.example.dietasapp.data.model.FoodModel
import com.example.dietasapp.databinding.FoodSearchLineBinding

interface FoodsInterface {
    fun setFoodsClickListener(food: FoodModel, binding: FoodSearchLineBinding)
}