package com.example.alfateam.presenter.sad

import com.example.alfateam.presenter.main.State

sealed interface StateSad{
    object Success: StateSad
    data class Start(val flagPermission:Boolean): StateSad
    object Loading: StateSad
    data class Error(val message:String,val flagLowMoral:Boolean): StateSad
}
