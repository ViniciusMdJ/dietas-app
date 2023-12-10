package com.example.dietasapp.UI.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dietasapp.UI.viewHolder.ListMealsViewHolder
import com.example.dietasapp.data.intefaces.MealsInterface
import com.example.dietasapp.data.model.MealModel
import com.example.dietasapp.databinding.MealLineBinding

class ListMealAdapter(private val clickMeal: MealsInterface): RecyclerView.Adapter<ListMealsViewHolder>() {

    private var mealList: List<MealModel> = listOf()
    private lateinit var binding: MealLineBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMealsViewHolder {
        binding = MealLineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return ListMealsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListMealsViewHolder, position: Int) {
        holder.bindVH(mealList[position])
        clickMeal.setMealsClickListener(mealList[position], binding)
    }

    override fun getItemCount(): Int {
        return mealList.count()
    }

    fun updateMealList(list: List<MealModel>) {
        mealList = list
        notifyDataSetChanged()
    }
}
