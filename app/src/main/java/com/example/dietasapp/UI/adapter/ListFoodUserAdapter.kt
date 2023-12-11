package com.example.dietasapp.UI.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dietasapp.UI.viewHolder.ListFoodUserViewHolder
import com.example.dietasapp.data.intefaces.FoodsUserInterface
import com.example.dietasapp.data.model.FoodUserModel
import com.example.dietasapp.databinding.FoodLineBinding

/**
 * Adapter for displaying a list of user-specific food items in a RecyclerView.
 *
 * This adapter is responsible for creating and binding views for each item in the user's food list.
 *
 * @param clickFood An interface for handling click events on user-specific food items.
 */
class ListFoodUserAdapter(private val clickFood: FoodsUserInterface) : RecyclerView.Adapter<ListFoodUserViewHolder>() {
    private var foodList: List<FoodUserModel> = listOf()
    private lateinit var binding: FoodLineBinding

    /**
     * Creates a new view holder by inflating the layout for a single item in the user's food list.
     *
     * @param parent The parent view group.
     * @param viewType The type of the view to be created.
     * @return A new instance of [ListFoodUserViewHolder].
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFoodUserViewHolder {
        binding = FoodLineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ListFoodUserViewHolder(binding)
    }

    /**
     * Binds the data of a user-specific food item to a view holder and sets up a click listener.
     *
     * @param holder The view holder to bind data to.
     * @param position The position of the item in the user's food list.
     */
    override fun onBindViewHolder(holder: ListFoodUserViewHolder, position: Int) {
        holder.bindVH(foodList[position])
        clickFood.setFoodsUserClickListener(foodList[position], binding)
    }

    /**
     * Returns the total number of user-specific food items in the list.
     *
     * @return The number of user-specific food items in the list.
     */
    override fun getItemCount(): Int {
        return foodList.count()
    }

    /**
     * Updates the user's food list with a new list of [FoodUserModel] items and notifies the adapter of the change.
     *
     * @param list The new list of [FoodUserModel] items.
     */
    fun updateFoodList(list: List<FoodUserModel>) {
        foodList = list
        notifyDataSetChanged()
    }
}