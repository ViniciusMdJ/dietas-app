package com.example.dietasapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietasapp.R
import com.example.dietasapp.data.Utils
import com.example.dietasapp.data.Utils.Firestore.FieldUserName
import com.example.dietasapp.data.Utils.Firestore.FieldUserPhoneNumber
import com.google.firebase.auth.FirebaseAuth

class UserProfileViewModel: ViewModel()  {
    private var name = MutableLiveData<String>()
    private var phone = MutableLiveData<String>()
    private var email = MutableLiveData<String>()
    private var msg = MutableLiveData<String>()
    private var isLoggedOut = MutableLiveData<Boolean>()

    init {
        isLoggedOut.value = false
    }

    fun getMsg(): MutableLiveData<String> {
        return msg
    }
    fun resetMsg(){
        msg = MutableLiveData<String>()
    }
    fun getName(): LiveData<String?> {
        return name
    }
    fun getPhone(): LiveData<String> {
        return phone
    }
    fun getEmail(): LiveData<String> {
        return email
    }
    fun logOutEvent(): LiveData<Boolean> {
        return isLoggedOut
    }

    fun getUserData(){
        val nameRef = Utils.Firestore.getUserData()
        email.value = Utils.Firestore.getUserEmail()
        nameRef.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documentSnapshot = task.result
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    name.value = documentSnapshot.getString(FieldUserName)
                    phone.value = documentSnapshot.getString(FieldUserPhoneNumber)
                }
            } else {
                Log.e("Utils", "Error getting user document", task.exception)
            }
        }
    }
    fun setNewUserData(newName: String, newPhone: String){
        val userDocRef = Utils.Firestore.getUserDocRef()

        // Update name and phone
        userDocRef.update(
            FieldUserName, newName,
            FieldUserPhoneNumber, newPhone
        ).addOnCompleteListener { updateTask ->
            if (updateTask.isSuccessful) {
                msg.value = R.string.update_success.toString()
                Log.d("UserProfileViewModel", "User data updated successfully")
            } else {
                msg.value = R.string.update_problem.toString()
                Log.e("UserProfileViewModel", "Error updating user data", updateTask.exception)
            }
        }
    }
    fun logOut(){
        Utils.Firestore.logOutFunction()
        isLoggedOut.value = true
    }
}