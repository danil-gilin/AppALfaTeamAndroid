package com.example.alfateam.presenter.dollar

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alfateam.R
import com.example.alfateam.domain.GetDollarUseCase
import com.example.alfateam.entity.Constance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DollarViewModel @Inject constructor(private val getDollarUseCase: GetDollarUseCase): ViewModel() {

    private val _stateDollar= MutableStateFlow<StateDollar>(StateDollar.Loading)
    val stateDollar= _stateDollar.asStateFlow()


    fun getDolar(){

        viewModelScope.launch {
          val time=System.currentTimeMillis()
            _stateDollar.value=StateDollar.Loading

            Log.d("timeDollar","${time-33119}")
              val course= getDollarUseCase.getDollar(time)
                if (course<120.0) {
                    _stateDollar.value = StateDollar.Succses(course.toString(), R.raw.snake,R.color.badOlegRed,R.string.OlegBad)
                }else{
                    _stateDollar.value = StateDollar.Succses(course.toString(), R.raw.beer,R.color.luckOlegGreen,R.string.OlegLucky)
                }

        }
    }
}