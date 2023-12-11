package com.example.dietasapp.UI.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dietasapp.UI.viewHolder.ListFoodViewHolder
import com.example.dietasapp.data.intefaces.FoodsInterface
import com.example.dietasapp.data.model.FoodModel
import com.example.dietasapp.databinding.FoodSearchLineBinding

class ListFoodAdapter(private val clickFood: FoodsInterface): RecyclerView.Adapter<ListFoodViewHolder>() {

    private var foodList: List<FoodModel> = listOf()
    private lateinit var binding: FoodSearchLineBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFoodViewHolder {
        binding = FoodSearchLineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return ListFoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListFoodViewHolder, position: Int) {
        holder.bindVH(foodList[position])
        clickFood.setFoodsClickListener(foodList[position], binding)
    }

    override fun getItemCount(): Int {
        return foodList.count()
    }

    fun updateFoodList(list: List<FoodModel>) {
        foodList = list
        notifyDataSetChanged()
    }
}