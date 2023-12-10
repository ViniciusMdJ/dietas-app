package com.example.dietasapp.UI

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TimePicker
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.dietasapp.R
import com.example.dietasapp.data.model.MealModel
import com.example.dietasapp.databinding.FragmentMealDialogBinding
import com.example.dietasapp.viewModel.MealsViewModel
import java.util.Calendar
import java.util.Date

class MealDialogFragment : DialogFragment(), View.OnClickListener, TimePickerDialog.OnTimeSetListener {
    private var _binding: FragmentMealDialogBinding? = null
    private val binding get() = _binding!!
    private val mealVM: MealsViewModel by activityViewModels()

    private lateinit var time: Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentMealDialogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createButtonMealDialog.setOnClickListener(this)
        binding.cancelButtonMealDialog.setOnClickListener(this)
        binding.timeButtonMealDialog.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v.id == R.id.create_button_meal_dialog){
            val title = binding.nameEditTextMealDialog.text.toString()
            if(title.isEmpty()){
                Toast.makeText(context, R.string.toast_fill_action, Toast.LENGTH_SHORT).show()
                return
            }
            if(!::time.isInitialized){
                Toast.makeText(context, R.string.toast_fill_time, Toast.LENGTH_SHORT).show()
                return
            }
            val m = MealModel(title, date=time.time)
            mealVM.createMeals(m)
            dismiss()
        }
        else if(v.id == R.id.cancel_button_meal_dialog){
            dismiss()
        }
        else if(v.id == R.id.time_button_meal_dialog){
            TimePickerDialog(context, this, 0, 0, true).show()
        }
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ConstraintLayout.LayoutParams.MATCH_PARENT
        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, sec: Int) {
        time = Calendar.getInstance()
        time.set(2000, 0, 1, hour, sec, 0)

        Toast.makeText(context, "${time.get(Calendar.HOUR_OF_DAY)}:${time.get(Calendar.MINUTE)}", Toast.LENGTH_SHORT).show()
    }


}