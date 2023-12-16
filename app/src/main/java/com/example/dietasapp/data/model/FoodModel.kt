package com.example.dietasapp.data.model

import java.io.Serializable

data class FoodModel(
    val title: String = "",
    var calorie: Int = 0,
    var fat: Double = 0.0,
    var protein: Double = 0.0,
    var carbohydrate: Double = 0.0,
    var id: String = ""
): Serializable
