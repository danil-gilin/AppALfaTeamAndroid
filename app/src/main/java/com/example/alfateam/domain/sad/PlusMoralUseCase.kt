package com.example.alfateam.domain.sad

import com.example.alfateam.data.SadRepository
import com.example.alfateam.entity.SadBoy
import javax.inject.Inject

class PlusMoralUseCase @Inject constructor(private val sadRepository: SadRepository) {
    suspend fun plusMoral(sadBoy: SadBoy,uid:String,time:Long){
    return sadRepository.plusMoral(sadBoy,uid,time)
    }
}