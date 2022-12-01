package com.example.alfateam.presenter.main

import com.example.alfateam.entity.Hero

sealed interface State{
    object Success:State
    object Start:State
    object Loading:State
    data class Error(val message:androidx.collection.ArrayMap<String,String?>):State
}