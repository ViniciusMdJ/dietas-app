package com.example.dietasapp.UI

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dietasapp.R
import com.example.dietasapp.UI.adapter.ListDietAdapter
import com.example.dietasapp.data.intefaces.DietsInterface
import com.example.dietasapp.data.model.DietModel
import com.example.dietasapp.data.model.UserModel
import com.example.dietasapp.databinding.DietLineBinding
import com.example.dietasapp.databinding.FragmentDietsBinding
import com.example.dietasapp.viewModel.DietsViewModel

/**
 * Fragment for displaying a list of diets.
 */
class DietsFragment : Fragment(), View.OnClickListener, DietsInterface {
    private var _binding: FragmentDietsBinding? = null
    private val binding get() = _binding!!
    private val dietVM: DietsViewModel by activityViewModels()
    private lateinit var adapter: ListDietAdapter
    private lateinit var user: UserModel

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

    /**
     * Sets observers for LiveData objects in the ViewModel.
     */
    private fun setObserver(){
        dietVM.getListDiets().observe(viewLifecycleOwner) {
            adapter.updateDietList(it)
        }
        dietVM.getUser().observe(viewLifecycleOwner) {
            user = it!!
        }
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.floating_action_button_add_diet){
            DietDialogFragment().show(parentFragmentManager, "dialog")
        }
    }

    /**
     * Implements the interface function to handle clicks on diet items.
     *
     * @param d The clicked DietModel.
     * @param binding The binding object for the clicked item view.
     */
    override fun setDietsClickListener(d: DietModel, binding: DietLineBinding) {
        binding.starIcon.setOnClickListener(){
            if(it.tag == true){
                dietVM.setFavoriteDiet("")
            }else{
                dietVM.setFavoriteDiet(d.id)
            }
        }
        dietVM.getFavoriteDiet().observe(viewLifecycleOwner) {
            if(it == d.id){
                binding.starIcon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.baseline_star_selected_24, null))
                binding.starIcon.tag = true
            }else{
                binding.starIcon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.baseline_star_unselected_24, null))
                binding.starIcon.tag = false
            }
        }
        binding.root.setOnClickListener {
            val action = DietsFragmentDirections.actionHomeFragmentToMealFragment(d)
            findNavController().navigate(action)
        }
        binding.editIconDiet.setOnClickListener {
            DietDialogFragment.newInstance(d).show(parentFragmentManager, "dialog")
        }
        binding.deleteIconDiet.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Deletar dieta")
                .setMessage("Deseja deletar a dieta ${d.title}?")
                .setPositiveButton("Sim") { _, _ ->
                    dietVM.deleteDiet(d.id)
                }
                .setNegativeButton("Não") { _, _ -> }
                .show()
        }
    }
}