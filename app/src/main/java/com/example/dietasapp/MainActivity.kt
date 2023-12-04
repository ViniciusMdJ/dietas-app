package com.example.dietasapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.dietasapp.databinding.ActivityMainBinding
import com.example.dietasapp.viewModel.MainViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainVM: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener(this)
        binding.registerText.setOnClickListener(this)

        mainVM = ViewModelProvider(this).get(MainViewModel::class.java)
        setObserver()
    }

    fun setObserver(){
        mainVM.getMsgFail().observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        mainVM.getIsAuth().observe(this){
            if(it){
                Toast.makeText(this, "Deu certo login", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onClick(v: View) {
        if(v.id == R.id.login_button){
            val email = binding.emailEditText.text.toString()
            val pass = binding.passwordEditText.text.toString()
            mainVM.signInWithEmailAndPassword(email, pass)
        }else if(v.id == R.id.register_text){
            Toast.makeText(this, "Clicou no cadastre-se", Toast.LENGTH_SHORT).show()
        }
    }
}