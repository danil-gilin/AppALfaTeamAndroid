package com.example.alfateam.domain.sad

import com.example.alfateam.data.SadRepository
import javax.inject.Inject

class CheckUserPermissionForMoralUseCase @Inject constructor(private val sadRepository: SadRepository){
    suspend fun checkUserPermission(uid:String,time:Long):Boolean{
        return sadRepository.checkUserPermission(uid,time)
    }
}