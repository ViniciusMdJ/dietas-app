package com.example.dietasapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietasapp.data.Utils
import com.example.dietasapp.data.model.MealModel

class MealsViewModel: ViewModel() {

    private var listMeals = MutableLiveData<MutableList<MealModel>>()
    private lateinit var dietId: String

    fun setDietId(id: String){
        dietId = id
    }

    fun getListMeals(): LiveData<MutableList<MealModel>> {
        return listMeals
    }

    fun updateAllMealsDB(){
        val colMealRef = Utils.Firestore.getUserMealsColRef(dietId)

        colMealRef.get().addOnSuccessListener {
            val list = mutableListOf<MealModel>()
            for (doc in it){
                val meal = doc.toObject(MealModel::class.java)
                meal.id = doc.id
                meal.dietId = dietId
                Log.i("MealsViewModelUpdate", "Refeição: $meal")
                list.add(meal)
            }
            listMeals.value = list
        }
            .addOnFailureListener {
                Log.e("MealsViewModel", "Erro ao buscar refeições")
            }
    }

    fun createMeals(meal: MealModel){
        val colMealRef = Utils.Firestore.getUserMealsColRef(dietId)
        colMealRef.add(meal)
            .addOnSuccessListener {
                Log.i("MealsViewModel", "Refeição criada com sucesso")
                updateAllMealsDB()
            }
            .addOnFailureListener {
                Log.e("MealsViewModel", "Erro ao criar refeição")
            }
    }

    fun updateMeals(meal: MealModel){
        Log.i("MealsViewModel", "Refeição: $meal")
        val docMealRef = Utils.Firestore.getUserMealsDocRef(meal.dietId, meal.id)
        docMealRef.update("title", meal.title, "date", meal.date)
            .addOnSuccessListener {
                Log.i("MealsViewModel", "Refeição atualizada com sucesso")
                updateAllMealsDB()
            }
            .addOnFailureListener {
                Log.e("MealsViewModel", "Erro ao atualizar refeição")
            }
    }

    fun deleteMeal(m: MealModel) {
        val docMealRef = Utils.Firestore.getUserMealsDocRef(m.dietId, m.id)
        docMealRef.delete()
            .addOnSuccessListener {
                Log.i("MealsViewModel", "Refeição deletada com sucesso")
                updateAllMealsDB()
            }
            .addOnFailureListener {
                Log.e("MealsViewModel", "Erro ao deletar refeição")
            }
    }
}