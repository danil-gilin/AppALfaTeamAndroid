package com.example.alfateam.presenter.dollar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class DollarViewModelFactory @Inject constructor(private val dollarViewModel: DollarViewModel) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return dollarViewModel as T
    }
}