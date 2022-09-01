package com.example.alfateam.domain

import android.text.BoringLayout
import com.example.alfateam.data.RepositoryAuth
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val  repositoryAuth: RepositoryAuth){

  suspend  fun logUser(email:String,password:String) :Boolean{
      return  repositoryAuth.logUser(email,password)
    }
}