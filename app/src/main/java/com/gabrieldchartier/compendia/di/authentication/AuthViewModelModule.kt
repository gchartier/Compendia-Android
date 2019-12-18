package com.gabrieldchartier.compendia.di.authentication

import androidx.lifecycle.ViewModel
import com.gabrieldchartier.compendia.di.ViewModelKey
import com.gabrieldchartier.compendia.ui.authentication.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: AuthViewModel): ViewModel

}