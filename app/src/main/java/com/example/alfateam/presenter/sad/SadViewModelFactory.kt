package com.example.alfateam.presenter.sad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class SadViewModelFactory @Inject constructor(private val sadViewModel: SadViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return sadViewModel as T

    }
}
