package com.example.dietasapp.data.intefaces

import com.example.dietasapp.data.model.FoodsUserModel
import com.example.dietasapp.databinding.FoodLineBinding

interface FoodsInterface {
    fun setFoodsClickListener(f: FoodsUserModel, binding: FoodLineBinding)
}