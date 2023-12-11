package com.example.dietasapp.UI

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dietasapp.R
import com.example.dietasapp.UI.adapter.ListDietAdapter
import com.example.dietasapp.UI.adapter.ListFoodAdapter
import com.example.dietasapp.data.Utils
import com.example.dietasapp.data.intefaces.FoodsInterface
import com.example.dietasapp.data.model.FoodModel
import com.example.dietasapp.data.model.FoodUserModel
import com.example.dietasapp.databinding.FoodSearchLineBinding
import com.example.dietasapp.databinding.FragmentFoodUserDialogBinding
import com.example.dietasapp.viewModel.DietsViewModel
import com.example.dietasapp.viewModel.FoodUserViewModel
import com.example.dietasapp.viewModel.FoodsDialogViewModel
import com.example.dietasapp.viewModel.MealsViewModel

class FoodUserDialogFragment : DialogFragment(), View.OnClickListener, FoodsInterface {
    private var _binding: FragmentFoodUserDialogBinding? = null
    private val binding get() = _binding!!
    private val foodUserVM: FoodUserViewModel by activityViewModels()
    private val foodDialogVM: FoodsDialogViewModel by viewModels()
    private lateinit var adapter: ListFoodAdapter

    private val mealVM: MealsViewModel by activityViewModels()
    private val dietVM: DietsViewModel by activityViewModels()

    private lateinit var foodUser: FoodUserModel
    private var food: FoodModel? = null

    companion object {
        fun newInstance(f: FoodUserModel): FoodUserDialogFragment {
            val dialog = FoodUserDialogFragment()

            val args = Bundle()
            args.putSerializable("foodUser", f)
            dialog.arguments = args
            return dialog
        }

        fun newInstance(): FoodUserDialogFragment {
            return FoodUserDialogFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments?.containsKey("foodUser") == true){
            foodUser = arguments?.getSerializable("foodUser") as FoodUserModel
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentFoodUserDialogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createButtonFoodDialog.setOnClickListener(this)
        binding.cancelButtonFoodDialog.setOnClickListener(this)

        adapter = ListFoodAdapter(this)

        binding.recyclerViewFoodDialog.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFoodDialog.adapter = adapter

        binding.nameSearchViewFoodDialog.setOnQueryTextListener(object :
        SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.recyclerViewFoodDialog.visibility = View.VISIBLE
                query?.let {
                    if (it.isNotEmpty()) foodDialogVM.searchFood(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        if(::foodUser.isInitialized){
            binding.nameSearchViewFoodDialog.setQuery(foodUser.food.title, false)
            binding.gramsEditTextFoodDialog.setText(foodUser.grams.toString())
            binding.descriptionEditTextFoodDialog.setText(foodUser.description)
        }

        setObserver()
    }

    private fun setObserver(){
        foodDialogVM.getListFoods().observe(viewLifecycleOwner) {
            Log.d("searchChange", it.toString())
            adapter.updateFoodList(it)
        }
        foodUserVM.getFood().observe(viewLifecycleOwner) {
            food = it
            binding.nameSearchViewFoodDialog.setQuery(it.title, false)
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.create_button_food_dialog) {
            val grams = binding.gramsEditTextFoodDialog.text.toString().toIntOrNull()?:-1
            val desc = binding.descriptionEditTextFoodDialog.text.toString()
            if (grams < 0 || desc.isEmpty()) {
                Toast.makeText(context, R.string.toast_fill_action, Toast.LENGTH_SHORT).show()
                return
            }
            if(food == null){
                Toast.makeText(context, R.string.toast_select_food, Toast.LENGTH_SHORT).show()
                return
            }
            val ref = food!!.id
            val fu = FoodUserModel(grams=grams, description=desc, foodReference="${Utils.Firestore.CollectionsFood}/${ref}")
            if(::foodUser.isInitialized){
                fu.dietId = foodUser.dietId
                fu.mealId = foodUser.mealId
                fu.id = foodUser.id
                mealVM.updatemacronutrients(foodUser.mealId, foodUser.food, food!!)
                dietVM.updatemacronutrients(foodUser.dietId, foodUser.food, food!!)
                foodUserVM.updateFoodUser(fu)
            } else {
                mealVM.updatemacronutrients(foodUserVM.getMealId(), food!!)
                dietVM.updatemacronutrients(foodUserVM.getDietId(), food!!)
                foodUserVM.createFoodUser(fu)
            }
            dismiss()
        } else if (v.id == R.id.cancel_button_food_dialog) {
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ConstraintLayout.LayoutParams.MATCH_PARENT
        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    override fun setFoodsClickListener(food: FoodModel, bindingLine: FoodSearchLineBinding) {
        bindingLine.root.setOnClickListener(){
            foodUserVM.setFood(food)
            binding.recyclerViewFoodDialog.visibility = View.GONE
            adapter.updateFoodList(listOf())
        }
    }
}