package com.example.dietasapp.data.intefaces

import com.example.dietasapp.data.model.FoodUserModel
import com.example.dietasapp.databinding.FoodLineBinding

interface FoodsInterface {
    fun setFoodsClickListener(f: FoodUserModel, binding: FoodLineBinding)
}