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
/**
 * ViewModel for managing authentication and user-related data.
 */
class MainViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private var isAuth = MutableLiveData<Boolean>()
    private var msgFail = MutableLiveData<String>()

    /**
     * Initializes the ViewModel by checking the current user authentication status.
     */
    init {
        isAuth.value = (auth.currentUser != null)
    }

    /**
     * Get LiveData for the user authentication status.
     * @return LiveData containing the authentication status.
     */
    fun getIsAuth(): LiveData<Boolean> {
        return isAuth
    }

    /**
     * Reset the LiveData for failure messages.
     */
    fun resetMsgFail(){
        msgFail = MutableLiveData<String>()
    }

    /**
     * Get LiveData for displaying failure messages.
     * @return LiveData containing the failure messages.
     */
    fun getMsgFail(): LiveData<String> {
        return msgFail
    }

    /**
     * Attempt to sign in with the provided email and password.
     * @param email The user's email.
     * @param pass The user's password.
     * @return Boolean Returns true if the sign-in request is initiated.
     */
    fun signInWithEmailAndPassword(email: String, pass: String): Boolean{
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

    /**
     * Create a new user account with the provided email, password, name, and phone number.
     * @param email The user's email.
     * @param pass The user's password.
     * @param name The user's name.
     * @param phoneNumber The user's phone number.
     */
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