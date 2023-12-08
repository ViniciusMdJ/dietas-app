package com.example.dietasapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietasapp.R
import com.example.dietasapp.data.Utils
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
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

    fun signInWithEmailAndPassword(email: String, pass: String) : Boolean{
        if (email.isEmpty()) {
            msgFail.value = R.string.toast_insert_email.toString()
            return false
        } else if (pass.isEmpty()) {
            msgFail.value = R.string.toast_insert_password.toString()
            return false
        }
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                isAuth.value = true
            }
        }.addOnFailureListener {
            msgFail.value = when (it) {
                is FirebaseNetworkException -> R.string.toast_internet_error.toString()
                else -> R.string.toast_wrong_user_password.toString()
            }
        }
        return true
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
                when (val exception = it.exception) {
                    is FirebaseAuthWeakPasswordException -> {
                        msgFail.value = R.string.toast_weak_password.toString()
                    }
                    is FirebaseAuthInvalidCredentialsException -> {
                        msgFail.value = R.string.toast_invalid_email.toString()
                    }
                    is FirebaseAuthUserCollisionException -> {
                        msgFail.value = R.string.toast_used_email.toString()
                    }
                    else -> {
                        msgFail.value = R.string.toast_generic_singup_error.toString()
                    }
                }
            }
        }
    }
}