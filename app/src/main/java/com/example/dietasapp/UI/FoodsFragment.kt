package com.example.dietasapp.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dietasapp.R
import com.example.dietasapp.UI.adapter.ListFoodAdapter
import com.example.dietasapp.data.intefaces.FoodsInterface
import com.example.dietasapp.data.model.FoodsUserModel
import com.example.dietasapp.databinding.FoodLineBinding
import com.example.dietasapp.databinding.FragmentFoodsBinding
import com.example.dietasapp.viewModel.FoodsViewModel

class FoodsFragment : Fragment(), View.OnClickListener, FoodsInterface {
    private var _binding: FragmentFoodsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ListFoodAdapter
    private val foodVM: FoodsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentFoodsBinding.inflate(inflater, container, false)

//        mealVM.setDietId(args.diet.id)
//        mealVM.updateAllMealsDB()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListFoodAdapter(this)

//        binding.dietTitleText.text = args.diet.title

        binding.recyclerListFoods.layoutManager = LinearLayoutManager(context)
        binding.recyclerListFoods.adapter = adapter

        binding.floatingActionButtonAddFoods.setOnClickListener(this)

        setObserver()
    }

    private fun setObserver(){
//        foodVM.getListMeals().observe(viewLifecycleOwner) {
//            adapter.updateMealList(it)
//        }
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.floating_action_button_add_foods){
            FoodDialogFragment().show(parentFragmentManager, "dialog")
        }
    }

    override fun setFoodsClickListener(f: FoodsUserModel, binding: FoodLineBinding) {
        Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
    }
}