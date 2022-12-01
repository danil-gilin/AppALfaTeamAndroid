package com.example.alfateam.domain.sad

import com.example.alfateam.data.SadRepository
import com.example.alfateam.entity.SadBoy
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoralUseCase @Inject constructor(private val sadRepository: SadRepository) {
  fun getMoral(): Flow<SadBoy> {
       return sadRepository.getMoral()
    }
}