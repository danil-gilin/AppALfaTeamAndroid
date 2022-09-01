package com.example.alfateam.presenter.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class RegistrationViewModelFactory @Inject constructor (val registrationViewModel: RegistrationViewModel):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return registrationViewModel as T
    }
}