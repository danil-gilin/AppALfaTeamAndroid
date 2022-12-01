package com.example.alfateam.presenter.registration

import android.util.Log
import android.util.Patterns
import androidx.collection.arrayMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.alfateam.domain.CheckUserLoginUseCase
import com.example.alfateam.domain.GetHeroUseCase
import com.example.alfateam.domain.RegistretionUserUseCase
import com.example.alfateam.entity.Hero
import com.example.alfateam.entity.User
import com.example.alfateam.presenter.main.State
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor (private val getHeroUseCase: GetHeroUseCase,
                                                 private val registrationUser: RegistretionUserUseCase,
                                                 private val checkUserLogin:CheckUserLoginUseCase): ViewModel() {
    private val _heroStatus= getHeroUseCase.getHero()
    val heroStatus=_heroStatus.asLiveData()

    private val _inputState= MutableStateFlow<State>(State.Start)
    val inputState=_inputState.asStateFlow()

    private val auth= FirebaseAuth.getInstance()

    init {

    }


    fun registrationUser( email: String, password: String,login: String,hero: Hero){
        registrationUser.regUser(email,password,login,hero)
    }

   fun checkInput(login: String, email: String, password: String,hero: Hero?) {
       viewModelScope.launch {
        _inputState.value=State.Loading
        var flagList= arrayListOf<Boolean>(false,false,false,false)
        val errorList =arrayMapOf<String,String?>(Pair("Login",null),Pair("Password",null),Pair("Email",null),Pair("Hero",null))
        if(login!="" ){
            flagList[0]=true
        }else{
            errorList["Login"]="Логин неверный"
        }

        if(checkUserLogin.checkUserLogin(login)){
            flagList[0]=true
        }else{
            errorList["Login"]="Логин занят"
        }

        if (password.length>6){
            flagList[1]=true
        }else{
            errorList["Password"]="Пароль сишком легкий"
        }

        if (isEmailValid(email)){
            flagList[2]=true
        }else{
            errorList["Email"]="Email неверный"
        }

        if (hero!=null){
            flagList[3]=true
        }else {
            errorList["Hero"]="Герой не выбран"
        }


        if(flagList.all { it==true }){
            _inputState.value=State.Success
            registrationUser(email, password,login, hero!!)
        }else{
            _inputState.value=State.Error(errorList)
        }

       }
    }

    fun isEmailValid(email: CharSequence?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}