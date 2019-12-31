package com.gabrieldchartier.compendia.di.main

import com.gabrieldchartier.compendia.api.main.CompendiaAPIMainService
import com.gabrieldchartier.compendia.persistence.authentication.AccountPropertiesDAO
import com.gabrieldchartier.compendia.repository.main.HomeRepository
import com.gabrieldchartier.compendia.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
class MainModule {
    @MainScope
    @Provides
    fun provideCompendiaAPIMainService(retrofitBuilder: Retrofit.Builder): CompendiaAPIMainService {
        return retrofitBuilder.build().create(CompendiaAPIMainService::class.java)
    }

    @MainScope
    @Provides
    fun provideHomeRepository(
            compendiaAPIMainService: CompendiaAPIMainService,
            accountPropertiesDAO: AccountPropertiesDAO,
            sessionManager: SessionManager
    ): HomeRepository
    {
        return HomeRepository(compendiaAPIMainService, accountPropertiesDAO, sessionManager)
    }
}