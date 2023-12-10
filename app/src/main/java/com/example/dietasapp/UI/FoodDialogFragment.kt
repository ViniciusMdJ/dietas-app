package com.example.dietasapp.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.dietasapp.R
import com.example.dietasapp.data.model.MealModel
import com.example.dietasapp.databinding.FragmentFoodDialogBinding
import com.example.dietasapp.databinding.FragmentMealDialogBinding
import com.example.dietasapp.viewModel.FoodsViewModel
import com.example.dietasapp.viewModel.MealsViewModel

class FoodDialogFragment : DialogFragment(), View.OnClickListener {
    private var _binding: FragmentFoodDialogBinding? = null
    private val binding get() = _binding!!
    private val mealVM: FoodsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentFoodDialogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createButtonFoodDialog.setOnClickListener(this)
        binding.createButtonFoodDialog.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.create_button_food_dialog) {
            val title = binding.nameEditTextFoodDialog.text.toString()
            val grams = binding.gramsEditTextFoodDialog.text.toString().toIntOrNull()?:-1
            val desc = binding.descriptionEditTextFoodDialog.text.toString()
            if (title.isEmpty() || grams < 0 || desc.isEmpty()) {
                Toast.makeText(context, R.string.toast_fill_action, Toast.LENGTH_SHORT).show()
                return
            }
            val m = MealModel(title)
            Toast.makeText(context, "Food created", Toast.LENGTH_SHORT).show()
//            mealVM.createMeals(m)
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
}