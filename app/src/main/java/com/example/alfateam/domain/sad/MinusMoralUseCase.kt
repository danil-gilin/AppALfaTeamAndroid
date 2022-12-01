package com.example.alfateam.domain.sad

import com.example.alfateam.data.SadRepository
import com.example.alfateam.entity.SadBoy
import javax.inject.Inject

class MinusMoralUseCase @Inject constructor(private val sadRepository: SadRepository) {
    suspend fun minusMoral(sadBoy: SadBoy,uid:String,time:Long){
        return sadRepository.minusMoral(sadBoy,uid,time)
    }
}