package com.example.dietasapp.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dietasapp.R
import com.example.dietasapp.databinding.FragmentLoginBinding
import com.example.dietasapp.viewModel.MainViewModel

class LoginFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val mainVM: MainViewModel by activityViewModels()

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

        setObserver()
    }

    fun setObserver(){
        mainVM.getMsgFail().observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        mainVM.getIsAuth().observe(viewLifecycleOwner){
            if(it){
                Toast.makeText(context, "Deu certo login", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(v: View) {
        if(v.id == R.id.login_button){
            val email = binding.emailEditText.text.toString()
            val pass = binding.passwordEditText.text.toString()
//            TODO("Implementar feedback para usuario login em andamento")
            mainVM.signInWithEmailAndPassword(email, pass)
//            Toast.makeText(context, "Clicou no login", Toast.LENGTH_SHORT).show()
        }else if(v.id == R.id.register_text){
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}