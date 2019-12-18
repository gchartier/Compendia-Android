package com.gabrieldchartier.compendia.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gabrieldchartier.compendia.models.AccountProperties
import com.gabrieldchartier.compendia.models.AuthToken
import com.gabrieldchartier.compendia.persistence.authentication.AccountPropertiesDAO
import com.gabrieldchartier.compendia.persistence.authentication.AuthTokenDAO

@Database(
        entities = [
            AuthToken::class,
            AccountProperties::class
        ],
        version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getAuthenticationTokenDAO(): AuthTokenDAO

    abstract fun getAccountPropertiesDAO(): AccountPropertiesDAO

    companion object {
        const val DATABASE_NAME = "application_database"
    }
}