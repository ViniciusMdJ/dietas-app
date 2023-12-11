package com.example.dietasapp.UI.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dietasapp.UI.viewHolder.ListFoodUserViewHolder
import com.example.dietasapp.data.intefaces.FoodsInterface
import com.example.dietasapp.data.model.FoodUserModel
import com.example.dietasapp.databinding.FoodLineBinding

class ListFoodUserAdapter(private val clickFood: FoodsInterface): RecyclerView.Adapter<ListFoodUserViewHolder>() {
    private var foodList: List<FoodUserModel> = listOf()
    private lateinit var binding: FoodLineBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFoodUserViewHolder {
        binding = FoodLineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return ListFoodUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListFoodUserViewHolder, position: Int) {
        holder.bindVH(foodList[position])
        clickFood.setFoodsClickListener(foodList[position], binding)
    }

    override fun getItemCount(): Int {
        return foodList.count()
    }

    fun updateFoodList(list: List<FoodUserModel>) {
        foodList = list
        notifyDataSetChanged()
    }
}