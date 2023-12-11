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
import java.text.SimpleDateFormat
import java.util.Calendar

class MealDialogFragment : DialogFragment(), View.OnClickListener, TimePickerDialog.OnTimeSetListener {
    private var _binding: FragmentMealDialogBinding? = null
    private val binding get() = _binding!!
    private val mealVM: MealsViewModel by activityViewModels()

    private lateinit var time: Calendar
    private lateinit var meal: MealModel
    private val sdf = SimpleDateFormat("HH:mm")

    companion object {
        fun newInstance(m: MealModel): MealDialogFragment {
            val f = MealDialogFragment()

            val args = Bundle()
            args.putSerializable("meal", m)
            f.arguments = args
            return f
        }

        fun newInstance(): MealDialogFragment {
            return MealDialogFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments?.containsKey("meal") == true){
            meal = arguments?.getSerializable("meal") as MealModel
        }

    }

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

        if(::meal.isInitialized){
            binding.nameEditTextMealDialog.setText(meal.title)
            binding.timeTextMealDialog.text = sdf.format(meal.date)
            time = Calendar.getInstance()
            time.time = meal.date
        }

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
            if(::meal.isInitialized){
                m.id = meal.id
                m.dietId = meal.dietId
                mealVM.updateMeals(m)
            }
            else{
                mealVM.createMeals(m)
            }
            dismiss()
        }
        else if(v.id == R.id.cancel_button_meal_dialog){
            dismiss()
        }
        else if(v.id == R.id.time_button_meal_dialog){
            var hour = 0
            var sec = 0
            if(::time.isInitialized){
                hour = time.get(Calendar.HOUR_OF_DAY)
                sec = time.get(Calendar.MINUTE)
            }

            TimePickerDialog(context, this, hour, sec, true).show()
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

        binding.timeTextMealDialog.text = sdf.format(time.time)
    }


}