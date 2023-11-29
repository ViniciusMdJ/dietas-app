package com.example.dietasapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class MainViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private var isAuth = MutableLiveData<Boolean>()
    private var msgFail = MutableLiveData<String>()

    init {
        isAuth.value = (auth.currentUser != null)
    }

    fun getIsAuth(): LiveData<Boolean> {
        return isAuth
    }

    fun getMsgFail(): LiveData<String> {
        return msgFail
    }

    fun signInWithEmailAndPassword(email: String, pass: String) {
        if (email.isEmpty()) {
            msgFail.value = "Insira o email"
            return
        } else if (pass.isEmpty()) {
            msgFail.value = "Insira a senha"
            return
        }
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                isAuth.value = true
            }
        }.addOnFailureListener {
            msgFail.value = when (it) {
                is FirebaseNetworkException -> "Falha ao conectar com a internet"
                else -> "Usuario ou senha estão errados"
            }
        }
    }
}