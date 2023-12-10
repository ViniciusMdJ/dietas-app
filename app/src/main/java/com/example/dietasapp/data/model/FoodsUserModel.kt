package com.example.dietasapp.data.model

data class FoodsUserModel(
    val title: String = "",
    val description: String = "",
    val calorie: Int = 0,
    val fat: Int = 0,
    val protein: Int = 0,
    val carbohydrate: Int = 0,
    val foodReference: String = "",
    var id: String = ""
)