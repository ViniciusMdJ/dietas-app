package com.example.dietasapp.UI

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.dietasapp.R
import com.example.dietasapp.databinding.FragmentLoginBinding
import com.example.dietasapp.viewModel.MainViewModel

class LoginFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val mainVM: MainViewModel by activityViewModels()
    private lateinit var loginProgress: DialogProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener(this)
        binding.registerText.setOnClickListener(this)
        binding.passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
        loginProgress = context?.let { DialogProgressBar(it, "Login...") }!!

        setObserver()
    }

    fun setObserver(){
        mainVM.resetMsgFail()
        mainVM.getMsgFail().observe(viewLifecycleOwner) {
            loginProgress.progressDialog?.dismiss()
            Toast.makeText(context, resources.getString(it.toInt()), Toast.LENGTH_SHORT).show()
        }

        mainVM.getIsAuth().observe(viewLifecycleOwner){
            if(it){
                loginProgress.progressDialog?.dismiss()
                Toast.makeText(context, R.string.toast_login_success, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(v: View) {
        if(v.id == R.id.login_button){
            val email = binding.emailEditText.text.toString()
            val pass = binding.passwordEditText.text.toString()

            loginProgress.progressDialog?.show()
            val response = mainVM.signInWithEmailAndPassword(email, pass)
            if(!response) {
                loginProgress.progressDialog?.dismiss()
            }
        }else if(v.id == R.id.register_text){
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}