package com.example.dietasapp.UI.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dietasapp.UI.viewHolder.ListMealsViewHolder
import com.example.dietasapp.data.intefaces.MealsInterface
import com.example.dietasapp.data.model.MealModel
import com.example.dietasapp.databinding.MealLineBinding
/**
 * RecyclerView Adapter for displaying a list of meal items.
 *
 * @param clickMeal The interface to handle meal item click events.
 */
class ListMealAdapter(private val clickMeal: MealsInterface) : RecyclerView.Adapter<ListMealsViewHolder>() {
    private var mealList: List<MealModel> = listOf()
    private lateinit var binding: MealLineBinding

    /**
     * Creates and returns a new [ListMealsViewHolder] for the meal item view.
     *
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The type of the new View.
     * @return A new ListMealsViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMealsViewHolder {
        binding = MealLineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ListMealsViewHolder(binding)
    }

    /**
     * Binds the data to the given [holder] at the specified [position].
     *
     * @param holder The ListMealsViewHolder to bind the data.
     * @param position The position of the item in the dataset.
     */
    override fun onBindViewHolder(holder: ListMealsViewHolder, position: Int) {
        holder.bindVH(mealList[position])
        clickMeal.setMealsClickListener(mealList[position], binding)
    }

    /**
     * Returns the total number of items in the dataset held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return mealList.size
    }

    /**
     * Updates the list of meal items and notifies the adapter of the data change.
     *
     * @param list The new list of meal items to be displayed.
     */
    fun updateMealList(list: List<MealModel>) {
        mealList = list
        notifyDataSetChanged()
    }
}