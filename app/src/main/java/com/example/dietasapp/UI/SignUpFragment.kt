package com.example.dietasapp.UI

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.dietasapp.R
import com.example.dietasapp.databinding.FragmentSignUpBinding
import com.example.dietasapp.viewModel.MainViewModel

class SignUpFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val mainVM: MainViewModel by activityViewModels()
    private var signUpProgress: DialogProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerButton.setOnClickListener(this)
        binding.passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
        binding.confirmPasswordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
        signUpProgress = context?.let { DialogProgressBar(it, "SignUp...") }

        setObserver()
    }

    fun setObserver(){
        mainVM.resetMsgFail()
        mainVM.getMsgFail().observe(viewLifecycleOwner) {
            signUpProgress?.progressDialog?.dismiss()
            Toast.makeText(context, resources.getString(it.toInt()), Toast.LENGTH_SHORT).show()
        }

        mainVM.getIsAuth().observe(viewLifecycleOwner){
            if(it){
                signUpProgress?.progressDialog?.dismiss()
                Toast.makeText(context, R.string.toast_login_success, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(v: View) {
        if(v.id == R.id.register_button){
            val name = binding.userEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val phoneNumber = binding.phoneEditText.unMasked
            val pass = binding.passwordEditText.text.toString()
            val confirmPass = binding.confirmPasswordEditText.text.toString()

            if(name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()){
                Toast.makeText(context, R.string.toast_set_all_fields, Toast.LENGTH_SHORT).show()
                return
            }
            if(pass != confirmPass){
                Toast.makeText(context, R.string.toast_password_diff, Toast.LENGTH_SHORT).show()
                return
            }

            signUpProgress?.progressDialog?.show()
            mainVM.createUserWithEmailAndPassword(email, pass, name, phoneNumber)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}