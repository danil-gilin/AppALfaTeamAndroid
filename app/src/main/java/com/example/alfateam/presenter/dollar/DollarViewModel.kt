package com.example.alfateam.presenter.dollar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alfateam.R
import com.example.alfateam.domain.GetDollarUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DollarViewModel @Inject constructor(private val getDollarUseCase: GetDollarUseCase): ViewModel() {

    private val _stateDollar= MutableStateFlow<StateDollar>(StateDollar.Loading)
    val stateDollar= _stateDollar.asStateFlow()


    fun getDolar(){
        viewModelScope.launch {
            _stateDollar.value=StateDollar.Loading
            try {
              val course= getDollarUseCase.getDollar()
                if (course.data.uSDRUB.toFloat()<120.0f) {
                    _stateDollar.value = StateDollar.Succses(course.data.uSDRUB, R.raw.snake,R.color.badOlegRed,R.string.OlegBad)
                }else{
                    _stateDollar.value = StateDollar.Succses(course.data.uSDRUB, R.raw.beer,R.color.luckOlegGreen,R.string.OlegLucky)
                }
            }catch (e:Exception){
                _stateDollar.value=StateDollar.Error(message = "Error")
            }
        }
    }
}