package com.gabrieldchartier.compendia.di.authentication

import androidx.lifecycle.ViewModel
import com.gabrieldchartier.compendia.di.ViewModelKey
import com.gabrieldchartier.compendia.ui.authentication.AuthenticationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthenticationViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthenticationViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: AuthenticationViewModel): ViewModel

}