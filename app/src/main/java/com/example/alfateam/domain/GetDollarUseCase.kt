package com.example.alfateam.domain

import com.example.alfateam.data.RepositoryDollar
import com.example.alfateam.entity.dollar.Dollar
import javax.inject.Inject

class GetDollarUseCase @Inject constructor(private val repositoryDollar: RepositoryDollar) {
    suspend fun getDollar():Dollar{
        return repositoryDollar.getDollar()
    }
}