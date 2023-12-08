package com.example.dietasapp.UI

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dietasapp.R
import com.example.dietasapp.UI.adapter.ListDietAdapter
import com.example.dietasapp.databinding.FragmentDietsBinding
import com.example.dietasapp.viewModel.DietsViewModel

class DietsFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentDietsBinding? = null
    private val binding get() = _binding!!
    private val dietVM: DietsViewModel by activityViewModels()
    private val adapter = ListDietAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentDietsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerListDiets.layoutManager = LinearLayoutManager(context)
        binding.recyclerListDiets.adapter = adapter

        binding.floatingActionButtonAddDiet.setOnClickListener(this)

        setObserver()
        dietVM.updateAllDietsDB()
    }

    private fun setObserver(){
        dietVM.getListDiets().observe(viewLifecycleOwner) {
            adapter.updateDietList(it)
        }
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.floating_action_button_add_diet){
//            val d = DietModel("titulo", "descrição")
//            dietVM.createDiet(d)
            DietDialogFragment().show(parentFragmentManager, "dialog")
        }
    }

    fun createDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_diet, null)
        dialogView

        val mDialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false)
            .setPositiveButton("OK", null)
            .setNegativeButton("Cancel", null)
    }
}