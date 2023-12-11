package com.example.dietasapp.data.model

import java.io.Serializable

data class FoodUserModel(
    val description: String = "",
    val grams: Int = 0,
    val foodReference: String = "",
    var food: FoodModel = FoodModel(),
    var id: String = "",
    var dietId: String = "",
    var mealId: String = ""
): Serializable