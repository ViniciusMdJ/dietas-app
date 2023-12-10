package com.example.dietasapp.data.model

import java.io.Serializable
import java.util.Date

data class MealModel(
    val title: String = "",
    val description: String = "",
    val calorie: Int = 0,
    val fat: Int = 0,
    val protein: Int = 0,
    val carbohydrate: Int = 0,
    val grams: Int = 0,
    val date: Date = Date(),
    var id: String = ""
): Serializable