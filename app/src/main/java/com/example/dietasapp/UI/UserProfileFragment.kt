package com.example.dietasapp.UI

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.dietasapp.R
import com.example.dietasapp.databinding.FragmentUserProfileBinding
import com.example.dietasapp.viewModel.UserProfileViewModel

class UserProfileFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!
    private val userProfileVM: UserProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener(this)
        binding.logoutButton.setOnClickListener(this)
        binding.deleteAccountButton.setOnClickListener(this)

        setObserver()
        userProfileVM.getUserData()
    }
    fun setObserver(){
        userProfileVM.getName().observe(viewLifecycleOwner) {
            binding.userEditText.setText(it)
        }
        userProfileVM.getPhone().observe(viewLifecycleOwner) {
            binding.phoneEditText.setText(it)
        }
        userProfileVM.getEmail().observe(viewLifecycleOwner) {
            binding.emailText.text = it
        }
        userProfileVM.resetMsg()
        userProfileVM.getMsg().observe(viewLifecycleOwner) {
//            signUpProgress?.progressDialog?.dismiss()
            Toast.makeText(context, resources.getString(it.toInt()), Toast.LENGTH_SHORT).show()
        }
    }
    override fun onClick(v: View) {
        if(v.id == R.id.save_button){
            val newName = binding.userEditText.text.toString()
            val newPhone = binding.phoneEditText.unMasked
            userProfileVM.setNewUserData(newName, newPhone)

        }else if(v.id == R.id.delete_account_button){

        }else if(v.id == R.id.logout_button){
            userProfileVM.logOut()

        }
    }
}