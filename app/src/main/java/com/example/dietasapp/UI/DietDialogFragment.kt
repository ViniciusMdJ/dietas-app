package com.example.dietasapp.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dietasapp.R
import com.example.dietasapp.UI.adapter.ListDietAdapter
import com.example.dietasapp.data.model.DietModel
import com.example.dietasapp.databinding.FragmentDietDialogBinding
import com.example.dietasapp.databinding.FragmentDietsBinding
import com.example.dietasapp.viewModel.DietsViewModel

class DietDialogFragment : DialogFragment(), View.OnClickListener {
    private var _binding: FragmentDietDialogBinding? = null
    private val binding get() = _binding!!
    private val dietVM: DietsViewModel by activityViewModels()

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
            dietVM.createDiet(d)
            dismiss()
        }
        else if(v.id == R.id.cancel_button_diet_dialog){
            dismiss()
        }
    }

}