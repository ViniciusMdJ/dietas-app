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

/**
 * Utility class for Firestore operations related to the application.
 */
class Utils {

    /**
     * Object representing Firestore operations.
     */
    object Firestore {
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

        /**
         * Gets the DocumentReference for the current user.
         *
         * @return The DocumentReference for the current user.
         */
        fun getUserDocRef(): DocumentReference {
            return db.collection(CollectionUser).document(auth.currentUser!!.uid)
        }

        /**
         * Gets the CollectionReference for the diets of the current user.
         *
         * @return The CollectionReference for the diets of the current user.
         */
        fun getUserDietsColRef(): CollectionReference {
            return getUserDocRef().collection(CollectionsDiet)
        }

        /**
         * Gets the DocumentReference for a specific diet of the current user.
         *
         * @param id The ID of the diet.
         * @return The DocumentReference for the specified diet.
         */
        fun getUserDietDocRef(id: String): DocumentReference {
            return getUserDietsColRef().document(id)
        }

        /**
         * Gets the CollectionReference for the meals of a specific diet of the current user.
         *
         * @param dietId The ID of the diet.
         * @return The CollectionReference for the meals of the specified diet.
         */
        fun getUserMealsColRef(dietId: String): CollectionReference {
            return getUserDietDocRef(dietId).collection(CollectionsMeal)
        }

        /**
         * Gets the DocumentReference for a specific meal of a specific diet of the current user.
         *
         * @param dietId The ID of the diet.
         * @param mealId The ID of the meal.
         * @return The DocumentReference for the specified meal.
         */
        fun getUserMealsDocRef(dietId: String, mealId: String): DocumentReference {
            return getUserMealsColRef(dietId).document(mealId)
        }

        /**
         * Gets the CollectionReference for the food users of a specific meal of a specific diet of the current user.
         *
         * @param dietId The ID of the diet.
         * @param mealId The ID of the meal.
         * @return The CollectionReference for the food users of the specified meal.
         */
        fun getUserFoodUsersColRef(dietId: String, mealId: String): CollectionReference {
            return getUserMealsDocRef(dietId, mealId).collection(CollectionsFoodUser)
        }

        /**
         * Gets the DocumentReference for a specific food user of a specific meal of a specific diet of the current user.
         *
         * @param dietId The ID of the diet.
         * @param mealId The ID of the meal.
         * @param foodUserId The ID of the food user.
         * @return The DocumentReference for the specified food user.
         */
        fun getUserFoodUserDocRef(dietId: String, mealId: String, foodUserId: String): DocumentReference {
            return getUserFoodUsersColRef(dietId, mealId).document(foodUserId)
        }

        fun getFoodColRef(): CollectionReference {
            return db.collection(CollectionsFood)
        }

        /**
         * Gets a LiveData object containing the user data of the current user.
         *
         * @return LiveData object containing the user data.
         */
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

        /**
         * Gets a Task object containing the user data of the current user.
         *
         * @return Task object containing the user data.
         */
        fun getUserData(): Task<DocumentSnapshot> {
            return getUserDocRef().get()
        }

        /**
         * Gets the email of the current user.
         *
         * @return The email of the current user.
         */
        fun getUserEmail(): String? {
            return auth.currentUser?.email
        }

        /**
         * Logs out the current user.
         */
        fun logOutFunction() {
            return auth.signOut()
        }
    }
}