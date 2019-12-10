package com.gabrieldchartier.compendia.di.authentication

import android.content.SharedPreferences
import com.gabrieldchartier.compendia.api.authentication.AuthenticationService
import com.gabrieldchartier.compendia.persistence.authentication.AccountPropertiesDAO
import com.gabrieldchartier.compendia.persistence.authentication.AuthenticationTokenDAO
import com.gabrieldchartier.compendia.repository.authentication.AuthenticationRepository
import com.gabrieldchartier.compendia.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class AuthenticationModule {

    @AuthenticationScope
    @Provides
    fun provideFakeApiService(retrofitBuilder: Retrofit.Builder): AuthenticationService{
        return retrofitBuilder
                .build()
                .create(AuthenticationService::class.java)
    }

    @AuthenticationScope
    @Provides
    fun provideAuthRepository(
            sessionManager: SessionManager,
            authTokenDao: AuthenticationTokenDAO,
            accountPropertiesDao: AccountPropertiesDAO,
            authenticationService: AuthenticationService,
            sharedPreferences: SharedPreferences,
            sharedPrefsEditor: SharedPreferences.Editor
    ): AuthenticationRepository {
        return AuthenticationRepository(
                authTokenDao,
                accountPropertiesDao,
                authenticationService,
                sessionManager,
                sharedPreferences,
                sharedPrefsEditor
        )
    }
}