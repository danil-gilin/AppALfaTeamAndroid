package com.example.alfateam.presenter.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alfateam.domain.GetUserFullInfoUseCase
import com.example.alfateam.entity.Hero
import com.example.alfateam.entity.User

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MenuViewModel @Inject constructor(private val getUserFullInfoUseCase: GetUserFullInfoUseCase) : ViewModel() {

    private val _state= MutableStateFlow<StateMenu>(StateMenu.Success(User("","", Hero("","","","",0,),System.currentTimeMillis())))
     val state=_state.asStateFlow()


    fun getUserInfo(token:String){
        viewModelScope.launch {
            _state.value=StateMenu.Loading
            try {
             val user= getUserFullInfoUseCase.getUser(token)
                _state.value=StateMenu.Success(user)
            }catch (e:Exception){
                Log.d("authcurrant",e.toString())
                _state.value=StateMenu.Error("Ошибка получения пользователя")
            }

        }
    }

}