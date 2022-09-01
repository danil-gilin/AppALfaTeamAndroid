package com.example.alfateam.domain

import com.example.alfateam.data.RepositoryAuth
import javax.inject.Inject

class CheckUserLoginUseCase @Inject constructor(private val repositoryAuth: RepositoryAuth) {

    suspend fun checkUserLogin(login:String):Boolean{
      return  repositoryAuth.checkUserLogin(login)
    }
}