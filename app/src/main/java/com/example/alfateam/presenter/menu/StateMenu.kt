package com.example.alfateam.presenter.menu

import com.example.alfateam.entity.User


sealed interface StateMenu{
    data class Success(val user: User):StateMenu
    object Loading:StateMenu
    data class Error(val message:String):StateMenu
}