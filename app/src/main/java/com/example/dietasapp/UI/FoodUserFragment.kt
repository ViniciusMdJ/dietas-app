package com.example.dietasapp.UI

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dietasapp.R
import com.example.dietasapp.UI.adapter.ListFoodUserAdapter
import com.example.dietasapp.data.intefaces.FoodsUserInterface
import com.example.dietasapp.data.model.FoodModel
import com.example.dietasapp.data.model.FoodUserModel
import com.example.dietasapp.databinding.FoodLineBinding
import com.example.dietasapp.databinding.FragmentFoodsBinding
import com.example.dietasapp.viewModel.DietsViewModel
import com.example.dietasapp.viewModel.FoodUserViewModel
import com.example.dietasapp.viewModel.MealsViewModel
import kotlinx.coroutines.flow.callbackFlow

/**
 * Fragment for displaying a list of food items associated with a meal.
 */
class FoodUserFragment : Fragment(), View.OnClickListener, FoodsUserInterface {
    private var _binding: FragmentFoodsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ListFoodUserAdapter
    private val args: FoodUserFragmentArgs by navArgs()
    private val foodUserVM: FoodUserViewModel by activityViewModels()

    private val mealVM: MealsViewModel by activityViewModels()
    private val dietVM: DietsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentFoodsBinding.inflate(inflater, container, false)
        foodUserVM.setMealId(args.meal.id)
        foodUserVM.setDietId(args.meal.dietId)

        foodUserVM.updateAllFoodUserDB()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListFoodUserAdapter(this)

        binding.recyclerListFoods.layoutManager = LinearLayoutManager(context)
        binding.recyclerListFoods.adapter = adapter

        binding.floatingActionButtonAddFoods.setOnClickListener(this)

        setObserver()
    }

    private fun setObserver(){
        foodUserVM.getListFoodUser().observe(viewLifecycleOwner) {
            adapter.updateFoodList(it)
        }
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.floating_action_button_add_foods){
            FoodUserDialogFragment().show(parentFragmentManager, "dialog")
        }
    }

    /**
     * Implements the interface function to handle clicks on food items.
     *
     * @param f The clicked FoodUserModel.
     * @param binding The binding object for the clicked item view.
     */
    override fun setFoodsUserClickListener(f: FoodUserModel, binding: FoodLineBinding) {
        binding.editIconFood.setOnClickListener {
            FoodUserDialogFragment.newInstance(f).show(parentFragmentManager, "dialog")
        }
        binding.deleteIconFood.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Deletar o Alimento")
                .setMessage("Deseja deletar o alimento ${f.food.title}?")
                .setPositiveButton("Sim") { _, _ ->
                    val foodNegative = FoodModel(
                        calorie=-f.food.calorie,
                        fat=-f.food.fat,
                        protein=-f.food.protein,
                        carbohydrate=-f.food.carbohydrate
                    )
                    mealVM.updatemacronutrients(args.meal.id, foodNegative)
                    dietVM.updatemacronutrients(args.meal.dietId, foodNegative)
                    foodUserVM.deleteFoodUser(f)
                }
                .setNegativeButton("Não") { _, _ -> }
                .show()
        }
    }
}