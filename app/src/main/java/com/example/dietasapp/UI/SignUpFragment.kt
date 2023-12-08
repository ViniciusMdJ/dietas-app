package com.example.dietasapp.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.dietasapp.R
import com.example.dietasapp.databinding.FragmentLoginBinding
import com.example.dietasapp.databinding.FragmentSignUpBinding
import com.example.dietasapp.viewModel.MainViewModel

class SignUpFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val mainVM: MainViewModel by activityViewModels()

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
        if(v.id == R.id.register_button){
            val name = binding.userEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val phoneNumber = binding.phoneEditText.text.toString()
            val pass = binding.passwordEditText.text.toString()
            val confirmPass = binding.confirmPasswordEditText.text.toString()

            if(name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()){
                Log.v("teste", "$name $email $phoneNumber $pass $confirmPass")
                Toast.makeText(context, "É nescessario preencher todos os campos", Toast.LENGTH_SHORT).show()
                return
            }
            if(pass != confirmPass){
                Toast.makeText(context, "As senhas estão diferentes", Toast.LENGTH_SHORT).show()
                return
            }
//            TODO("Implementar feedback para usuario que esta sendo criado o usuario")

            mainVM.createUserWithEmailAndPassword(email, pass, name, phoneNumber)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}