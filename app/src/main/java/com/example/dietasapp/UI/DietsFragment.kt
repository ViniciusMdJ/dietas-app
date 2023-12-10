package com.example.dietasapp.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dietasapp.R
import com.example.dietasapp.UI.adapter.ListDietAdapter
import com.example.dietasapp.data.intefaces.DietsInterface
import com.example.dietasapp.data.model.DietModel
import com.example.dietasapp.data.model.MealModel
import com.example.dietasapp.databinding.DietLineBinding
import com.example.dietasapp.databinding.FragmentDietsBinding
import com.example.dietasapp.viewModel.DietsViewModel

class DietsFragment : Fragment(), View.OnClickListener, DietsInterface {
    private var _binding: FragmentDietsBinding? = null
    private val binding get() = _binding!!
    private val dietVM: DietsViewModel by activityViewModels()
    private lateinit var adapter: ListDietAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentDietsBinding.inflate(inflater, container, false)

        dietVM.updateAllDietsDB()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListDietAdapter(this)

        binding.recyclerListDiets.layoutManager = LinearLayoutManager(context)
        binding.recyclerListDiets.adapter = adapter

        binding.floatingActionButtonAddDiet.setOnClickListener(this)

        setObserver()
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

    override fun setDietsClickListener(d: DietModel, binding: DietLineBinding) {
        binding.root.setOnClickListener {
            val action = DietsFragmentDirections.actionHomeFragmentToMealFragment(d)
            findNavController().navigate(action)
        }
        binding.editIconDiet.setOnClickListener {
            Toast.makeText(context, "Clicou em editar ${d.title}", Toast.LENGTH_SHORT).show()
//            TODO("Implementar a edição de dieta")
        }
    }
}