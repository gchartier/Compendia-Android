package com.gabrieldchartier.compendia.di.authentication

import com.gabrieldchartier.compendia.ui.authentication.ForgotPasswordFragment
import com.gabrieldchartier.compendia.ui.authentication.LauncherFragment
import com.gabrieldchartier.compendia.ui.authentication.LoginFragment
import com.gabrieldchartier.compendia.ui.authentication.RegisterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AuthenticationFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeLauncherFragment(): LauncherFragment

    @ContributesAndroidInjector()
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector()
    abstract fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector()
    abstract fun contributeForgotPasswordFragment(): ForgotPasswordFragment

}