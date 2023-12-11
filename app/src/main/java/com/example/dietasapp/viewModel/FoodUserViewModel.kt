package com.example.dietasapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietasapp.data.Utils
import com.example.dietasapp.data.model.FoodModel
import com.example.dietasapp.data.model.FoodUserModel
import com.example.dietasapp.data.model.MealModel
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FoodUserViewModel: ViewModel() {

    private var listFoodUser = MutableLiveData<MutableList<FoodUserModel>>()
    private lateinit var dietId: String
    private lateinit var mealId: String
    private var food = MutableLiveData<FoodModel>()

    fun getListFoodUser(): LiveData<MutableList<FoodUserModel>> {
        return listFoodUser
    }

    fun getFood(): LiveData<FoodModel> {
        return food
    }

    fun setDietId(id: String){
        dietId = id
    }

    fun setMealId(id: String){
        mealId = id
    }

    fun setFood(f: FoodModel){
        food.value = f
    }

    fun updateAllFoodUserDB() {
        viewModelScope.launch {
            try {
                val colFoodUserRef = Utils.Firestore.getUserFoodUsersColRef(dietId, mealId)

                val querySnapshot = withContext(Dispatchers.IO) {
                    colFoodUserRef.get().await()
                }

                val list = mutableListOf<FoodUserModel>()

                for (doc in querySnapshot) {
                    val foodUser = doc.toObject(FoodUserModel::class.java)
                    foodUser.id = doc.id
                    foodUser.dietId = dietId

                    val docFood = withContext(Dispatchers.IO) {
                        Utils.Firestore.getDocRef(foodUser.foodReference).get().await()
                    }

                    val multipler = foodUser.grams / 100
                    val food = docFood.toObject(FoodModel::class.java)
                    if (food != null) {
                        Log.i("FoodUserViewModelUpdate", "Alimento: $food")
                        food.calorie *= multipler
                        food.fat *= multipler
                        food.protein *= multipler
                        food.carbohydrate *= multipler
                        food.id = docFood.id

                        foodUser.food = food
                    }

                    list.add(foodUser)
                    Log.i("FoodUserViewModelUpdate", "Refeição: $foodUser")
                }

                listFoodUser.value = list
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("FoodUserViewModel", "Erro ao buscar refeições")
            }
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
        docFoodUserRef.update("title", foodUser.food.title, "description", foodUser.description, "grams", foodUser.grams)
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

    fun getMealId(): String {
        return mealId
    }

    fun getDietId(): String {
        return dietId
    }

}
