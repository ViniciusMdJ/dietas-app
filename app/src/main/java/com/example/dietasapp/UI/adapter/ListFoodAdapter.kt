package com.example.dietasapp.UI.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dietasapp.UI.viewHolder.ListFoodViewHolder
import com.example.dietasapp.data.intefaces.FoodsInterface
import com.example.dietasapp.data.model.FoodModel
import com.example.dietasapp.databinding.FoodSearchLineBinding

/**
 * Adapter for displaying a list of food items in a RecyclerView.
 *
 * This adapter is responsible for creating and binding views for each item in the food list.
 *
 * @param clickFood An interface for handling click events on food items.
 */
class ListFoodAdapter(private val clickFood: FoodsInterface) : RecyclerView.Adapter<ListFoodViewHolder>() {

    private var foodList: List<FoodModel> = listOf()
    private lateinit var binding: FoodSearchLineBinding

    /**
     * Creates a new view holder by inflating the layout for a single item in the food list.
     *
     * @param parent The parent view group.
     * @param viewType The type of the view to be created.
     * @return A new instance of [ListFoodViewHolder].
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFoodViewHolder {
        binding = FoodSearchLineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ListFoodViewHolder(binding)
    }

    /**
     * Binds the data of a food item to a view holder and sets up a click listener.
     *
     * @param holder The view holder to bind data to.
     * @param position The position of the item in the food list.
     */
    override fun onBindViewHolder(holder: ListFoodViewHolder, position: Int) {
        holder.bindVH(foodList[position])
        clickFood.setFoodsClickListener(foodList[position], binding)
    }

    /**
     * Returns the total number of items in the food list.
     *
     * @return The number of items in the food list.
     */
    override fun getItemCount(): Int {
        return foodList.count()
    }

    /**
     * Updates the food list with a new list of food items and notifies the adapter of the change.
     *
     * @param list The new list of [FoodModel] items.
     */
    fun updateFoodList(list: List<FoodModel>) {
        foodList = list
        notifyDataSetChanged()
    }
}