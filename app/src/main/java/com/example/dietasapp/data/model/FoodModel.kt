package com.example.dietasapp.data.model

import java.io.Serializable

data class FoodModel(
    val title: String = "",
    var calorie: Int = 0,
    var fat: Int = 0,
    var protein: Int = 0,
    var carbohydrate: Int = 0,
    var id: String = ""
): Serializable
