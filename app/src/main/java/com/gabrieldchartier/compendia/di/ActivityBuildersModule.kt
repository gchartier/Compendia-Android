package com.gabrieldchartier.compendia.di

import com.gabrieldchartier.compendia.di.authentication.AuthFragmentBuildersModule
import com.gabrieldchartier.compendia.di.authentication.AuthModule
import com.gabrieldchartier.compendia.di.authentication.AuthScope
import com.gabrieldchartier.compendia.di.authentication.AuthViewModelModule
import com.gabrieldchartier.compendia.di.main.MainFragmentBuildersModule
import com.gabrieldchartier.compendia.di.main.MainModule
import com.gabrieldchartier.compendia.di.main.MainScope
import com.gabrieldchartier.compendia.di.main.MainViewModelModule
import com.gabrieldchartier.compendia.ui.authentication.AuthActivity
import com.gabrieldchartier.compendia.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
            modules = [AuthModule::class, AuthFragmentBuildersModule::class,
                AuthViewModelModule::class]
    )
    abstract fun contributeAuthActivity(): AuthActivity

    @MainScope
    @ContributesAndroidInjector(
            modules = [MainModule::class, MainFragmentBuildersModule::class, MainViewModelModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity

}