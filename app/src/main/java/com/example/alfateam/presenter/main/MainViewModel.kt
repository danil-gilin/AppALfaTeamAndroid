package com.example.alfateam.presenter.main

import android.util.Log
import android.util.Patterns
import androidx.collection.arrayMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alfateam.domain.CheckUserLoginUseCase
import com.example.alfateam.domain.GetHeroUseCase
import com.example.alfateam.domain.LoginUserUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val loginUserUseCase: LoginUserUseCase): ViewModel() {

    private val _inputState= MutableStateFlow<State>(State.Success)
    val inputState=_inputState.asStateFlow()

   private val auth=FirebaseAuth.getInstance()

  fun checkInput(email: String, password: String,sidorCheck:Boolean) {
       viewModelScope.launch {
           _inputState.value = State.Loading
           var flagList = arrayListOf<Boolean>(false, false, sidorCheck, false)
           val errorList = arrayMapOf<String, String?>(
               Pair("Password", null),
               Pair("Email", null),
               Pair("Sidor", null),
               Pair("Registration", null)
           )

           if (password != "") {
               flagList[0] = true
           } else {
               errorList["Password"] = "Пароль пуст"
           }

           if (isEmailValid(email)) {
               flagList[1] = true
           } else {
               errorList["Email"] = "Email неверный формат"
           }

           if (!sidorCheck) {
               errorList["Sidor"] = "Sidor not checked"
           }

               if (flagList[0] && flagList[1] && flagList[2]) {
                   if (loginUserUseCase.logUser(email, password)){
                       flagList[3]=true
                       Log.d("Registration", "true")
                   }else{
                       errorList["Registration"] = "Email and Password is invalid"
                       Log.d("Registration", "fail")
                   }
               } else {
                   _inputState.value = State.Error(errorList)
               }

           if (flagList[3]){
               _inputState.value = State.Success
           }else{
               _inputState.value = State.Error(errorList)
           }

       }
    }

    fun isEmailValid(email: CharSequence?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}