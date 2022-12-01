package com.example.alfateam.presenter.dollar

sealed interface StateDollar{
    data class Succses(val course:String,val lottie:Int,val color:Int,val message: Int):StateDollar
    object Loading:StateDollar
   data class Error(val message:String):StateDollar
}