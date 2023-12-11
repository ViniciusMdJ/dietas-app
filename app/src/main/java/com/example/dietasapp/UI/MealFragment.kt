package com.example.dietasapp.UI

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dietasapp.R
import com.example.dietasapp.UI.adapter.ListMealAdapter
import com.example.dietasapp.data.intefaces.MealsInterface
import com.example.dietasapp.data.model.MealModel
import com.example.dietasapp.databinding.FragmentMealBinding
import com.example.dietasapp.databinding.MealLineBinding
import com.example.dietasapp.viewModel.MealsViewModel

class MealFragment : Fragment(), View.OnClickListener, MealsInterface {
    private var _binding: FragmentMealBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ListMealAdapter
    private val args: MealFragmentArgs by navArgs()
    private val mealVM: MealsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentMealBinding.inflate(inflater, container, false)

        mealVM.setDietId(args.diet.id)
        mealVM.updateAllMealsDB()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListMealAdapter(this)

        binding.dietTitleText.text = args.diet.title

        binding.recyclerListMeals.layoutManager = LinearLayoutManager(context)
        binding.recyclerListMeals.adapter = adapter

        binding.floatingActionButtonAddMeal.setOnClickListener(this)

        setObserver()
    }

    private fun setObserver(){
        mealVM.getListMeals().observe(viewLifecycleOwner) {
            adapter.updateMealList(it)
        }
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.floating_action_button_add_meal){
            MealDialogFragment().show(parentFragmentManager, "dialog")
        }
    }

    override fun setMealsClickListener(m: MealModel, binding: MealLineBinding) {
        binding.root.setOnClickListener {
            val action = MealFragmentDirections.actionMealFragmentToFoodsFragment(m)
            Log.d("TAG", "setMealsClickListener: $m")
            findNavController().navigate(action)
        }
        binding.editIconMeal.setOnClickListener {
            MealDialogFragment.newInstance(m).show(parentFragmentManager, "dialog")
        }
        binding.deleteIconMeal.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Deletar Refeição")
                .setMessage("Deseja deletar a refeição ${m.title}?")
                .setPositiveButton("Sim") { _, _ ->
                    mealVM.deleteMeal(m)
                }
                .setNegativeButton("Não") { _, _ -> }
                .show()
        }
    }
}