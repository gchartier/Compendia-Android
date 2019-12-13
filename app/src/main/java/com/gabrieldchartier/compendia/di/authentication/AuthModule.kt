package com.gabrieldchartier.compendia.di.authentication

import android.content.SharedPreferences
import com.gabrieldchartier.compendia.api.authentication.AuthService
import com.gabrieldchartier.compendia.persistence.authentication.AccountPropertiesDAO
import com.gabrieldchartier.compendia.persistence.authentication.AuthTokenDAO
import com.gabrieldchartier.compendia.repository.authentication.AuthRepository
import com.gabrieldchartier.compendia.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class AuthModule {

    @AuthScope
    @Provides
    fun provideAuthService(retrofitBuilder: Retrofit.Builder): AuthService{
        return retrofitBuilder
                .build()
                .create(AuthService::class.java)
    }

    @AuthScope
    @Provides
    fun provideAuthRepository(
            sessionManager: SessionManager,
            authTokenDao: AuthTokenDAO,
            accountPropertiesDao: AccountPropertiesDAO,
            authService: AuthService,
            sharedPreferences: SharedPreferences,
            sharedPrefsEditor: SharedPreferences.Editor): AuthRepository {
        return AuthRepository(
                authTokenDao,
                accountPropertiesDao,
                authService,
                sessionManager,
                sharedPreferences,
                sharedPrefsEditor)
    }
}