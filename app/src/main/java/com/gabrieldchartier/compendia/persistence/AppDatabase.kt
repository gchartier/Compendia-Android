package com.gabrieldchartier.compendia.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gabrieldchartier.compendia.models.*
import com.gabrieldchartier.compendia.persistence.authentication.AccountPropertiesDAO
import com.gabrieldchartier.compendia.persistence.authentication.AuthTokenDAO
import com.gabrieldchartier.compendia.persistence.main.NewReleasesDAO

@TypeConverters(Converters::class)
@Database(
        entities = [
            AuthToken::class,
            AccountProperties::class,
            Comic::class,
            Series::class,
            Publisher::class,
            Creator::class,
            ComicCreatorJoin::class,
            NewRelease::class
        ],
        version = 6
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getAuthenticationTokenDAO(): AuthTokenDAO

    abstract fun getAccountPropertiesDAO(): AccountPropertiesDAO

    abstract fun getNewReleasesDAO(): NewReleasesDAO

    companion object {
        const val DATABASE_NAME = "application_database"
    }
}