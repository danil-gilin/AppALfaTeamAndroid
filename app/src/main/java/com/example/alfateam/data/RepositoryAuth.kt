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
    private  val dbFirestoreHero = FirebaseFirestore.getInstance().collection("Hero")
    private  var localUser:User?=null

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

    suspend fun getUser(token:String):User{
    if(localUser!=null){
        Log.d("user","local")
        return localUser!!

    }else {
        val userHash = dbFirestoreUser.document(token).get().await()
        val heroHash = dbFirestoreHero.document(userHash.getString("Hero")!!).get().await()

        val hero = Hero(
            heroHash.id,
            heroHash.getString("Name")!!,
            heroHash.getString("Img_url")!!,
            heroHash.getString("Description")!!,
            heroHash.get("Rating").toString().toInt()
        )
            localUser=User(token, userHash.getString("Login") as String, hero)
        return User(token, userHash.getString("Login") as String, hero)

    }
    }
}