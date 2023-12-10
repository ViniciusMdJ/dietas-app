package com.example.dietasapp.data.model

import java.io.Serializable

data class DietModel(
    val title: String = "",
    val description: String = "",
    val calorie: Int = 0,
    val fat: Int = 0,
    val protein: Int = 0,
    val carbohydrate: Int = 0,
    var id: String = ""
): Serializable
