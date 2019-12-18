package com.gabrieldchartier.compendia.di

import android.app.Application
import com.gabrieldchartier.compendia.BaseApplication
import com.gabrieldchartier.compendia.session.SessionManager
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            AppModule::class,
            ActivityBuildersModule::class,
            ViewModelFactoryModule::class
        ]
)
interface AppComponent : AndroidInjector<BaseApplication>{

    val sessionManager: SessionManager // Injecting into abstract class and not through constructor

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
