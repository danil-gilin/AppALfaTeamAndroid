package com.example.alfateam.presenter.sad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.alfateam.domain.sad.CheckUserPermissionForMoralUseCase
import com.example.alfateam.domain.sad.GetMoralUseCase
import com.example.alfateam.domain.sad.MinusMoralUseCase
import com.example.alfateam.domain.sad.PlusMoralUseCase
import com.example.alfateam.entity.SadBoy
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SadViewModel @Inject constructor(
    private val getMoralUseCase: GetMoralUseCase,
    private val minusMoralUseCase: MinusMoralUseCase,
    private val plusMoralUseCase: PlusMoralUseCase,
    private val checkUserPermissionForMoralUseCase: CheckUserPermissionForMoralUseCase
) : ViewModel() {
    private var _moral = getMoralUseCase.getMoral()
    val moral = _moral.asLiveData()

    private val _state = MutableStateFlow<StateSad>(StateSad.Start(false))
    val state = _state.asStateFlow()

    private val auth= FirebaseAuth.getInstance()

    init {
        viewModelScope.launch{
            val flagPermission=  checkUserPermissionForMoralUseCase.checkUserPermission(auth.uid.toString(),System.currentTimeMillis())
            _state.value = StateSad.Start(flagPermission)
        }
    }


    fun minusMoral(sadBoy: SadBoy) {
        viewModelScope.launch {
            _state.value=StateSad.Loading
            if(sadBoy.moral-10>0) {
                sadBoy.moral = sadBoy.moral - 10
                try {
                    minusMoralUseCase.minusMoral(sadBoy,auth.uid.toString(),System.currentTimeMillis())
                    _state.value = StateSad.Success
                    val flagPermission=  checkUserPermissionForMoralUseCase.checkUserPermission(auth.uid.toString(),System.currentTimeMillis())
                    _state.value = StateSad.Start(flagPermission)
                } catch (e: Exception) {
                    _state.value = StateSad.Error("Ошибка изменения морали",false)
                }
            }else{
                _state.value = StateSad.Error("Ошибка изменения морали",true)
            }
        }
    }

    fun plusMoral(sadBoy: SadBoy) {
        viewModelScope.launch {
            _state.value=StateSad.Loading
            sadBoy.moral=sadBoy.moral+10
            try {
               plusMoralUseCase.plusMoral(sadBoy,auth.uid.toString(),System.currentTimeMillis())
                _state.value=StateSad.Success
                val flagPermission=  checkUserPermissionForMoralUseCase.checkUserPermission(auth.uid.toString(),System.currentTimeMillis())
                _state.value =StateSad.Start(flagPermission)
            }catch (e:Exception){
                _state.value=StateSad.Error("Ошибка изменения морали",false)
            }
        }
    }
}