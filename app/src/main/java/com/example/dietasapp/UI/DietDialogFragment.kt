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
import com.example.dietasapp.data.model.DietModel
import com.example.dietasapp.databinding.FragmentDietDialogBinding
import com.example.dietasapp.viewModel.DietsViewModel

/**
 * DialogFragment for creating or editing a diet.
 */
class DietDialogFragment : DialogFragment(), View.OnClickListener {
    private var _binding: FragmentDietDialogBinding? = null
    private val binding get() = _binding!!
    private val dietVM: DietsViewModel by activityViewModels()
    private lateinit var diet: DietModel

    /**
     * Companion object providing factory methods for creating instances of DietDialogFragment.
     */
    companion object {

        /**
         * Creates a new instance of DietDialogFragment with the specified [diet].
         *
         * @param diet The DietModel to be edited.
         * @return A new instance of DietDialogFragment.
         */
        fun newInstance(d: DietModel): DietDialogFragment {
            val f = DietDialogFragment()

            val args = Bundle()
            args.putSerializable("diet", d)
            f.arguments = args
            return f
        }

        /**
         * Creates a new instance of DietDialogFragment for creating a new diet.
         *
         * @return A new instance of DietDialogFragment.
         */
        fun newInstance(): DietDialogFragment {
            return DietDialogFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments?.containsKey("diet") == true){
            diet = arguments?.getSerializable("diet") as DietModel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentDietDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(::diet.isInitialized){
            binding.nameEditTextDietDialog.setText(diet.title)
            binding.descriptionEditTextDietDialog.setText(diet.description)
        }

        binding.createButtonDietDialog.setOnClickListener(this)
        binding.cancelButtonDietDialog.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v.id == R.id.create_button_diet_dialog){
            val title = binding.nameEditTextDietDialog.text.toString()
            val description = binding.descriptionEditTextDietDialog.text.toString()
            if(title.isEmpty() || description.isEmpty()){
                Toast.makeText(context, R.string.toast_fill_action, Toast.LENGTH_SHORT).show()
                return
            }
            val d = DietModel(title, description)
            if(::diet.isInitialized){
                d.id = diet.id
                dietVM.updateDiet(d)
            }else{
                dietVM.createDiet(d)
            }
            dismiss()
        }
        else if(v.id == R.id.cancel_button_diet_dialog){
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