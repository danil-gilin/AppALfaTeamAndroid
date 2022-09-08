package com.example.alfateam.domain

import com.example.alfateam.data.RepositoryAuth
import com.example.alfateam.entity.User
import javax.inject.Inject

class GetUserFullInfoUseCase @Inject constructor(private val repositoryAuth: RepositoryAuth) {
    suspend fun getUser(token:String): User {
       return repositoryAuth.getUser(token)
    }
}