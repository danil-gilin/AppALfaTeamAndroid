package com.example.alfateam.domain

import com.example.alfateam.data.RepositoryHero
import com.example.alfateam.entity.Hero
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHeroUseCase @Inject constructor (private val repositoryHero: RepositoryHero) {

fun getHero(): Flow<List<Hero>> {
        return repositoryHero.getHero()
    }
}