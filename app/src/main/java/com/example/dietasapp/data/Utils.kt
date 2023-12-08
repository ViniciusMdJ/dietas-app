package com.example.dietasapp.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class Utils {
    object Firestore{
        private val db = FirebaseFirestore.getInstance()
        private val auth = FirebaseAuth.getInstance()
        const val CollectionUser = "usuarios"
        const val CollectionsDiet = "dietas"
        const val CollectionsMeal = "refeicoes"
        const val FieldUserPhoneNumber = "numeroTelefone"
        const val FieldUserName = "nome"

        fun getUserDocRef(): DocumentReference {
            return db.collection(CollectionUser).document(auth.currentUser!!.uid)
        }

        fun getUserDietsColRef(): CollectionReference {
            return getUserDocRef().collection(CollectionsDiet)
        }
    }
}