package com.gabrieldchartier.compendia.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.gabrieldchartier.compendia.R
import com.gabrieldchartier.compendia.persistence.authentication.AccountPropertiesDAO
import com.gabrieldchartier.compendia.persistence.AppDatabase
import com.gabrieldchartier.compendia.persistence.AppDatabase.Companion.DATABASE_NAME
import com.gabrieldchartier.compendia.persistence.authentication.AuthTokenDAO
import com.gabrieldchartier.compendia.util.Constants
import com.gabrieldchartier.compendia.util.LiveDataCallAdapterFactory
import com.gabrieldchartier.compendia.util.PreferenceKeys
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideApplicationDB(app: Application): AppDatabase {
        return Room
                .databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration() // Get correct DB version if the schema changed
                .build()
    }

    @Singleton
    @Provides
    fun provideAuthenticationTokenDAO(db: AppDatabase): AuthTokenDAO {
        return db.getAuthenticationTokenDAO()
    }

    @Singleton
    @Provides
    fun provideAccountPropertiesDAO(db: AppDatabase): AccountPropertiesDAO {
        return db.getAccountPropertiesDAO()
    }

    @Singleton
    @Provides
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions
                .placeholderOf(R.drawable.default_image)
                .error(R.drawable.default_image)
    }

    @Singleton
    @Provides
    fun provideGlideInstance(application: Application, requestOptions: RequestOptions): RequestManager {
        return Glide.with(application)
                .setDefaultRequestOptions(requestOptions)
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences(PreferenceKeys.APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSharedPreferencesEditor(sharedPreferences: SharedPreferences): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

}