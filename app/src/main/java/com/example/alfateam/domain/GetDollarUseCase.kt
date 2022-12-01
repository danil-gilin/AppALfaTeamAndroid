package com.example.alfateam.domain

import com.example.alfateam.data.RepositoryDollar
import com.example.alfateam.entity.dollar_to_rub.DollarToRub
import javax.inject.Inject

class GetDollarUseCase @Inject constructor(private val repositoryDollar: RepositoryDollar) {
    suspend fun getDollar(time:Long):Double {
        return repositoryDollar.getDollar(time)
    }
}