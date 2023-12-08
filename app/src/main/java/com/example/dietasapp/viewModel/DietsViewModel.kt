package com.example.dietasapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietasapp.data.Utils
import com.example.dietasapp.data.model.DietModel

class DietsViewModel: ViewModel() {

    private var listDiets = MutableLiveData<MutableList<DietModel>>()

    fun getListDiets(): LiveData<MutableList<DietModel>>{
        return listDiets
    }

    fun updateAllDietsDB(){
        var colDietRef = Utils.Firestore.getUserDietsColRef()

        colDietRef.get().addOnSuccessListener {
            var list = mutableListOf<DietModel>()
            for (doc in it){
                var diet = doc.toObject(DietModel::class.java)
                list.add(diet)
            }
            listDiets.value = list
        }
            .addOnFailureListener {
                Log.e("DietsViewModel", "Erro ao buscar dietas")
            }
    }

    fun createDiet(diet: DietModel){
        var colDietRef = Utils.Firestore.getUserDietsColRef()
        colDietRef.add(diet)
            .addOnSuccessListener {
                Log.i("DietsViewModel", "Dieta criada com sucesso")
                updateAllDietsDB()
            }
            .addOnFailureListener {
                Log.e("DietsViewModel", "Erro ao criar dieta")
            }
    }
}