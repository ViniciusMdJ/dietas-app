package com.example.dietasapp.UI.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dietasapp.UI.viewHolder.ListFoodUserViewHolder
import com.example.dietasapp.data.intefaces.FoodsInterface
import com.example.dietasapp.data.model.FoodUserModel
import com.example.dietasapp.databinding.FoodLineBinding
/**
 * RecyclerView Adapter for displaying a list of food items associated with a user.
 *
 * @param clickFood The interface to handle food item click events.
 */
class ListFoodUserAdapter(private val clickFood: FoodsInterface) : RecyclerView.Adapter<ListFoodUserViewHolder>() {
    private var foodList: List<FoodUserModel> = listOf()
    private lateinit var binding: FoodLineBinding

    /**
     * Creates and returns a new [ListFoodUserViewHolder] for the food item view.
     *
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The type of the new View.
     * @return A new ListFoodUserViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFoodUserViewHolder {
        binding = FoodLineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ListFoodUserViewHolder(binding)
    }

    /**
     * Binds the data to the given [holder] at the specified [position].
     *
     * @param holder The ListFoodUserViewHolder to bind the data.
     * @param position The position of the item in the dataset.
     */
    override fun onBindViewHolder(holder: ListFoodUserViewHolder, position: Int) {
        holder.bindVH(foodList[position])
        clickFood.setFoodsClickListener(foodList[position], binding)
    }

    /**
     * Returns the total number of items in the dataset held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return foodList.size
    }

    /**
     * Updates the list of food items and notifies the adapter of the data change.
     *
     * @param list The new list of food items to be displayed.
     */
    fun updateFoodList(list: List<FoodUserModel>) {
        foodList = list
        notifyDataSetChanged()
    }
}
