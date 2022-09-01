package com.example.alfateam.data

import android.util.Log
import com.example.alfateam.entity.Hero
import com.example.alfateam.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepositoryAuth @Inject constructor() {
    private val auth= FirebaseAuth.getInstance()
    private  val dbFirestoreUser = FirebaseFirestore.getInstance().collection("User")

    fun regUser(email: String, password: String,login:String,hero: Hero) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            if(it.user?.uid !=null){
                val user= User(it.user!!.uid,login,hero)
                putUserDB(user)
            }
        }

    }

    suspend fun checkUserLogin(login: String):Boolean{
     return dbFirestoreUser.get().await().documents.all { it->it.get("Login")!=login }
    }


   suspend fun logUser(email: String, password: String):Boolean {
       var flag=true
       try {
           auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
               it.user?.let { it1 -> Log.d("Registration", it1.uid) }
           }.addOnFailureListener { e ->
               Log.w("Registration", "Error writing document", e)
               flag = false
           }.await()
       }catch (e:Exception){
           flag = false
       }
       return flag
    }

    fun putUserDB(user:User){
        val userHashMap= hashMapOf("Login" to user.login,"Hero" to user.hero.id)
        dbFirestoreUser.document(user.uid).set(userHashMap).addOnSuccessListener {
            Log.d("Registration", "DocumentSnapshot successfully written!")
        }.addOnFailureListener{
                e -> Log.w("Registration", "Error writing document", e)
        }
    }

    fun getUser(){

    }
}