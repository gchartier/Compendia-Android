package com.gabrieldchartier.compendia.di

import com.gabrieldchartier.compendia.di.authentication.AuthFragmentBuildersModule
import com.gabrieldchartier.compendia.di.authentication.AuthModule
import com.gabrieldchartier.compendia.di.authentication.AuthScope
import com.gabrieldchartier.compendia.di.authentication.AuthViewModelModule
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

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

}