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
/**
 * ViewModel for managing user profile data.
 */
class UserProfileViewModel : ViewModel() {
    private var name = MutableLiveData<String>()
    private var phone = MutableLiveData<String>()
    private var email = MutableLiveData<String>()
    private var msg = MutableLiveData<String>()
    private var isLoggedOut = MutableLiveData<Boolean>()

    init {
        isLoggedOut.value = false
    }

    /**
     * Get LiveData for the message.
     * @return LiveData containing the message.
     */
    fun getMsg(): LiveData<String> {
        return msg
    }

    /**
     * Reset the message LiveData.
     */
    fun resetMsg() {
        msg = MutableLiveData<String>()
    }

    /**
     * Get LiveData for the user's name.
     * @return LiveData containing the user's name.
     */
    fun getName(): LiveData<String?> {
        return name
    }

    /**
     * Get LiveData for the user's phone number.
     * @return LiveData containing the user's phone number.
     */
    fun getPhone(): LiveData<String> {
        return phone
    }

    /**
     * Get LiveData for the user's email.
     * @return LiveData containing the user's email.
     */
    fun getEmail(): LiveData<String> {
        return email
    }

    /**
     * Get LiveData for the log out event.
     * @return LiveData indicating the log out event.
     */
    fun logOutEvent(): LiveData<Boolean> {
        return isLoggedOut
    }

    /**
     * Get user data from Firestore and update LiveData.
     */
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

    /**
     * Set new user data and update Firestore.
     * @param newName The new name to be set.
     * @param newPhone The new phone number to be set.
     */
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

    /**
     * Log out the user and trigger the log out event.
     */
    fun logOut(){
        Utils.Firestore.logOutFunction()
        isLoggedOut.value = true
    }
}