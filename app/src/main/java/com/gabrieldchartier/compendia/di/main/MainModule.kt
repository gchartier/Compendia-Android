package com.gabrieldchartier.compendia.di.main

import android.content.SharedPreferences
import com.gabrieldchartier.compendia.api.main.CompendiaAPIMainService
import com.gabrieldchartier.compendia.persistence.AppDatabase
import com.gabrieldchartier.compendia.persistence.authentication.AccountPropertiesDAO
import com.gabrieldchartier.compendia.persistence.main.CollectionDAO
import com.gabrieldchartier.compendia.persistence.main.ComicBoxDAO
import com.gabrieldchartier.compendia.persistence.main.NewReleasesDAO
import com.gabrieldchartier.compendia.repository.authentication.AuthRepository
import com.gabrieldchartier.compendia.repository.main.HomeRepository
import com.gabrieldchartier.compendia.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
class MainModule {
    @MainScope
    @Provides
    fun provideCompendiaAPIMainService(retrofitBuilder: Retrofit.Builder): CompendiaAPIMainService {
        return retrofitBuilder.build().create(CompendiaAPIMainService::class.java)
    }

    @MainScope
    @Provides
    fun provideNewReleasesDAO(db: AppDatabase): NewReleasesDAO {
        return db.getNewReleasesDAO()
    }

    @MainScope
    @Provides
    fun provideComicBoxDAO(db: AppDatabase): ComicBoxDAO {
        return db.getComicBoxDAO()
    }

    @MainScope
    @Provides
    fun provideCollectionDAO(db: AppDatabase): CollectionDAO {
        return db.getCollectionDAO()
    }

    @MainScope
    @Provides
    fun provideHomeRepository(
            compendiaAPIMainService: CompendiaAPIMainService,
            accountPropertiesDAO: AccountPropertiesDAO,
            newReleasesDAO: NewReleasesDAO,
            comicBoxDAO: ComicBoxDAO,
            collectionDAO: CollectionDAO,
            sessionManager: SessionManager,
            sharedPreferences: SharedPreferences,
            sharedPrefsEditor: SharedPreferences.Editor
    ): HomeRepository
    {
        return HomeRepository(
                compendiaAPIMainService,
                accountPropertiesDAO,
                newReleasesDAO,
                comicBoxDAO,
                collectionDAO,
                sessionManager,
                sharedPreferences,
                sharedPrefsEditor
        )
    }
}