package com.example.alfateam.domain

import com.example.alfateam.data.RepositoryAuth
import com.example.alfateam.entity.Hero
import com.example.alfateam.entity.User
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class RegistretionUserUseCase @Inject constructor(private val repositoryAuth: RepositoryAuth) {


   fun regUser(email: String, password: String,login:String,hero:Hero){
        repositoryAuth.regUser(email, password,login,hero)
    }
}