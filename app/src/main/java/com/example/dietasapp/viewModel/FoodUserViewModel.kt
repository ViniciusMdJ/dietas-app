package com.example.dietasapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietasapp.data.Utils
import com.example.dietasapp.data.model.FoodUserModel
import com.example.dietasapp.data.model.MealModel

class FoodUserViewModel: ViewModel() {

    private var listFoodUser = MutableLiveData<MutableList<FoodUserModel>>()
    private lateinit var dietId: String
    private lateinit var mealId: String

    fun getListFoodUser(): LiveData<MutableList<FoodUserModel>> {
        return listFoodUser
    }

    fun setDietId(id: String){
        dietId = id
    }

    fun setMealId(id: String){
        mealId = id
    }

    fun updateAllFoodUserDB(){
        val colFoodUserRef = Utils.Firestore.getUserFoodUsersColRef(dietId, mealId)

        colFoodUserRef.get().addOnSuccessListener {
            val list = mutableListOf<FoodUserModel>()
            for (doc in it){
                val foodUser = doc.toObject(FoodUserModel::class.java)
                foodUser.id = doc.id
                foodUser.dietId = dietId
                Log.i("FoodUserViewModelUpdate", "Refeição: $foodUser")
                list.add(foodUser)
            }
            listFoodUser.value = list
        }
            .addOnFailureListener {
                Log.e("FoodUserViewModel", "Erro ao buscar refeições")
            }
    }

    fun createFoodUser(foodUser: FoodUserModel){
        val colMFoodUserRef = Utils.Firestore.getUserFoodUsersColRef(dietId, mealId)
        colMFoodUserRef.add(foodUser)
            .addOnSuccessListener {
                Log.i("FoodUserViewModel", "Refeição criada com sucesso")
                updateAllFoodUserDB()
            }
            .addOnFailureListener {
                Log.e("FoodUserViewModel", "Erro ao criar refeição")
            }
    }

    fun updateFoodUser(foodUser: FoodUserModel){
        val docFoodUserRef = Utils.Firestore.getUserFoodUserDocRef(dietId, mealId, foodUser.id)
        docFoodUserRef.update("title", foodUser.title, "description", foodUser.description, "grams", foodUser.grams)
            .addOnSuccessListener {
                Log.i("FoodUserViewModel", "Refeição atualizada com sucesso")
                updateAllFoodUserDB()
            }
            .addOnFailureListener {
                Log.e("FoodUserViewModel", "Erro ao atualizar refeição")
            }
    }

    fun deleteFoodUser(fu: FoodUserModel) {
        val docFoodUserRef = Utils.Firestore.getUserFoodUserDocRef(dietId, mealId, fu.id)
        docFoodUserRef.delete()
            .addOnSuccessListener {
                Log.i("FoodUserViewModel", "Refeição deletada com sucesso")
                updateAllFoodUserDB()
            }
            .addOnFailureListener {
                Log.e("FoodUserViewModel", "Erro ao deletar refeição")
            }
    }

}
