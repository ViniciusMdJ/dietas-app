package com.example.dietasapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietasapp.data.Utils
import com.example.dietasapp.data.model.FoodModel

class FoodsDialogViewModel: ViewModel() {
    private var listFoods = MutableLiveData<MutableList<FoodModel>>()

    fun getListFoods(): LiveData<MutableList<FoodModel>> {
        return listFoods
    }

    fun searchFood(search: String){
        val list = mutableListOf<FoodModel>()
        val colFoodRef = Utils.Firestore.getFoodColRef()
            .whereEqualTo("title", search)
        colFoodRef.get().addOnSuccessListener {
            for (doc in it.documents){
                val food = doc.toObject(FoodModel::class.java)
                if (food != null) {
                    food.id = doc.id
                    list.add(food)
                }
            }
            listFoods.value = list
        }
    }
}