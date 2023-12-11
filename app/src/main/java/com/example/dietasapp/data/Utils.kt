package com.example.dietasapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dietasapp.data.model.UserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class Utils {
    object Firestore{
        private val db = FirebaseFirestore.getInstance()
        private val auth = FirebaseAuth.getInstance()
        const val CollectionUser = "usuarios"
        const val CollectionsDiet = "dietas"
        const val CollectionsMeal = "refeicoes"
        const val CollectionsFoodUser = "alimentosUsuario"
        const val CollectionsFood = "alimentos"
        const val FieldUserPhoneNumber = "numeroTelefone"
        const val FieldUserName = "nome"

        fun getDocRef(path: String): DocumentReference {
            return db.document(path)
        }

        fun getUserDocRef(): DocumentReference {
            return db.collection(CollectionUser).document(auth.currentUser!!.uid)
        }

        fun getUserDietsColRef(): CollectionReference {
            return getUserDocRef().collection(CollectionsDiet)
        }

        fun getUserDietDocRef(id: String): DocumentReference {
            return getUserDietsColRef().document(id)
        }

        fun getUserMealsColRef(dietId: String): CollectionReference {
            return getUserDietDocRef(dietId).collection(CollectionsMeal)
        }

        fun getUserMealsDocRef(dietId: String, mealId: String): DocumentReference {
            return getUserMealsColRef(dietId).document(mealId)
        }

        fun getUserFoodUsersColRef(dietId: String, mealId: String): CollectionReference {
            return getUserMealsDocRef(dietId, mealId).collection(CollectionsFoodUser)
        }

        fun getUserFoodUserDocRef(dietId: String, mealId: String, foodUserId: String): DocumentReference {
            return getUserFoodUsersColRef(dietId, mealId).document(foodUserId)

        }

        fun getFoodColRef(): CollectionReference {
            return db.collection(CollectionsFood)
        }

        fun getUserLiveData(): LiveData<UserModel?> {
            val liveData = MutableLiveData<UserModel?>()
            getUserDocRef().get().addOnSuccessListener {
                val user = it.toObject(UserModel::class.java)
                if (user != null) {
                    user.email = auth.currentUser!!.email!!
                }

                liveData.setValue(user)

            }
            return liveData
        }

        fun getUserData(): Task<DocumentSnapshot> {
            return getUserDocRef().get()
        }

        fun getUserEmail(): String? {
            return auth.currentUser?.email
        }

        fun logOutFunction() {
            return auth.signOut()
        }
    }
}