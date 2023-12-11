package com.example.dietasapp.UI.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dietasapp.UI.viewHolder.ListDietsViewHolder
import com.example.dietasapp.data.intefaces.DietsInterface
import com.example.dietasapp.data.model.DietModel
import com.example.dietasapp.databinding.DietLineBinding

/**
 * RecyclerView Adapter for displaying a list of diets.
 *
 * @param clickDiet The interface to handle diet item click events.
 */
class ListDietAdapter(private val clickDiet: DietsInterface) : RecyclerView.Adapter<ListDietsViewHolder>() {
    private var dietList: List<DietModel> = listOf()
    private lateinit var binding: DietLineBinding

    /**
     * Creates and returns a new [ListDietsViewHolder] for the diet item view.
     *
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The type of the new View.
     * @return A new ListDietsViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDietsViewHolder {
        binding = DietLineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ListDietsViewHolder(binding)
    }

    /**
     * Binds the data to the given [holder] at the specified [position].
     *
     * @param holder The ListDietsViewHolder to bind the data.
     * @param position The position of the item in the dataset.
     */
    override fun onBindViewHolder(holder: ListDietsViewHolder, position: Int) {
        holder.bindVH(dietList[position])
        clickDiet.setDietsClickListener(dietList[position], binding)
    }

    /**
     * Returns the total number of items in the dataset held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return dietList.count()
    }

    /**
     * Updates the list of diets and notifies the adapter of the data change.
     *
     * @param list The new list of diets to be displayed.
     */
    fun updateDietList(list: List<DietModel>) {
        dietList = list
        notifyDataSetChanged()
    }
}

