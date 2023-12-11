package com.example.dietasapp.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.dietasapp.R
import com.example.dietasapp.data.model.FoodUserModel
import com.example.dietasapp.databinding.FragmentFoodUserDialogBinding
import com.example.dietasapp.viewModel.FoodUserViewModel

/**
 * DialogFragment for creating or updating a FoodUserModel.
 */
class FoodUserDialogFragment : DialogFragment(), View.OnClickListener {
    private var _binding: FragmentFoodUserDialogBinding? = null
    private val binding get() = _binding!!
    private val foodUserVM: FoodUserViewModel by activityViewModels()
    private lateinit var foodUser: FoodUserModel

    companion object {
        /**
         * Creates a new instance of FoodUserDialogFragment with the given FoodUserModel.
         *
         * @param f The FoodUserModel to be passed to the dialog.
         * @return A new instance of FoodUserDialogFragment.
         */
        fun newInstance(f: FoodUserModel): FoodUserDialogFragment {
            val dialog = FoodUserDialogFragment()

            val args = Bundle()
            args.putSerializable("foodUser", f)
            dialog.arguments = args
            return dialog
        }

        /**
         * Creates a new instance of FoodUserDialogFragment without any initial data.
         *
         * @return A new instance of FoodUserDialogFragment.
         */
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
        binding.createButtonFoodDialog.setOnClickListener(this)

        if(::foodUser.isInitialized){
            binding.nameEditTextFoodDialog.setText(foodUser.title)
            binding.gramsEditTextFoodDialog.setText(foodUser.grams.toString())
            binding.descriptionEditTextFoodDialog.setText(foodUser.description)
        }
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
            val fu = FoodUserModel(title=title, grams=grams, description=desc)
            if(::foodUser.isInitialized){
                fu.dietId = foodUser.dietId
                fu.mealId = foodUser.mealId
                fu.id = foodUser.id
                foodUserVM.updateFoodUser(fu)
            } else {
                foodUserVM.createFoodUser(foodUser)
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
}