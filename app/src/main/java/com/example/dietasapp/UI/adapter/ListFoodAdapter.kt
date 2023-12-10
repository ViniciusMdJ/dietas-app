package com.example.dietasapp.UI.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dietasapp.UI.viewHolder.ListFoodsViewHolder
import com.example.dietasapp.data.intefaces.FoodsInterface
import com.example.dietasapp.data.model.FoodsUserModel
import com.example.dietasapp.databinding.FoodLineBinding

class ListFoodAdapter(private val clickFood: FoodsInterface): RecyclerView.Adapter<ListFoodsViewHolder>() {
    private var foodList: List<FoodsUserModel> = listOf()
    private lateinit var binding: FoodLineBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFoodsViewHolder {
        binding = FoodLineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return ListFoodsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListFoodsViewHolder, position: Int) {
        holder.bindVH(foodList[position])
        clickFood.setFoodsClickListener(foodList[position], binding)
    }

    override fun getItemCount(): Int {
        return foodList.count()
    }

    fun updateFoodList(list: List<FoodsUserModel>) {
        foodList = list
        notifyDataSetChanged()
    }
}