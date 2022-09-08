package com.example.alfateam.presenter.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class MenuViewModelFactory @Inject constructor(private val menuViewModel: MenuViewModel):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return menuViewModel as T
    }
}