package com.example.dietasapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietasapp.data.Utils
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private var isAuth = MutableLiveData<Boolean>()
    private var msgFail = MutableLiveData<String>()

    init {
        auth.signOut()
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

    fun createUserWithEmailAndPassword(email: String, pass: String, name: String, phoneNumber: String) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    isAuth.value = true
                    val userMap = hashMapOf(
                        Utils.Firestore.FieldUserName to name,
                        Utils.Firestore.FieldUserPhoneNumber to phoneNumber
                    )
                    val currUserId = auth.currentUser?.uid
                    if (currUserId != null) {
                        db.collection("usuarios").document(currUserId)
                            .set(userMap)
                            .addOnSuccessListener {
                                Log.d("Firebase", "Salvo com sucesso")
                            }
                            .addOnFailureListener{
                                Log.d("Firebase", "Problema ao salvar: ", it)
                            }
                    }



                } else {
                    msgFail.value = "Falha ao cadastrar usuario, ${it.exception.toString()}"
                }
            }
    }
}