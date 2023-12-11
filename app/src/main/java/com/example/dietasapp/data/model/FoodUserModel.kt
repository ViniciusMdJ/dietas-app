package com.example.dietasapp.data.model

import java.io.Serializable

data class FoodUserModel(
    val title: String = "",
    val description: String = "",
    val calorie: Int = 0,
    val fat: Int = 0,
    val protein: Int = 0,
    val carbohydrate: Int = 0,
    val grams: Int = 0,
    val foodReference: String = "",
    var id: String = "",
    var dietId: String = "",
    var mealId: String = ""
): Serializable