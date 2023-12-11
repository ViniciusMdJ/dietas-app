package com.example.dietasapp.data.intefaces

import com.example.dietasapp.data.model.FoodUserModel
import com.example.dietasapp.databinding.FoodLineBinding

interface FoodsUserInterface {
    fun setFoodsUserClickListener(f: FoodUserModel, binding: FoodLineBinding)
}