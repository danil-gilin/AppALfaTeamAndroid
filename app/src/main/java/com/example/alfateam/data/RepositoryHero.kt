package com.example.alfateam.data

import android.util.Log
import com.example.alfateam.entity.Hero
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoryHero @Inject constructor () {
    private val dbFirestoreHero:FirebaseFirestore=FirebaseFirestore.getInstance()


   fun getHero(): Flow<List<Hero>> {
        var listHero= MutableStateFlow<List<Hero>>(emptyList())

        dbFirestoreHero.collection("Hero")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val tempListHero= arrayListOf<Hero>()
                    querySnapshot.documents.forEach {
                        tempListHero.add(Hero(it.id,
                            it.get("Name").toString(),
                            it.get("Img_url").toString(),
                            it.get("Description").toString(),
                            it.get("Rating").toString().toInt()))
                    }
                    listHero.value=tempListHero
         }
        return listHero
    }
}