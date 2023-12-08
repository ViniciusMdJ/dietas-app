package com.example.dietasapp.UI.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dietasapp.UI.viewHolder.ListDietsViewHolder
import com.example.dietasapp.data.model.DietModel
import com.example.dietasapp.databinding.DietLineBinding

class ListDietAdapter : RecyclerView.Adapter<ListDietsViewHolder>() {

    private var dietList: List<DietModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDietsViewHolder {
        val item = DietLineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return ListDietsViewHolder(item)
    }

    override fun onBindViewHolder(holder: ListDietsViewHolder, position: Int) {
        holder.bindVH(dietList[position])
    }

    override fun getItemCount(): Int {
        return dietList.count()
    }

    fun updateDietList(list: List<DietModel>) {
        dietList = list
        notifyDataSetChanged()
    }
}
