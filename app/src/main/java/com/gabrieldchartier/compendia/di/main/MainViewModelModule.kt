package com.gabrieldchartier.compendia.di.main

import androidx.lifecycle.ViewModel
import com.gabrieldchartier.compendia.di.ViewModelKey
import com.gabrieldchartier.compendia.ui.main.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

}