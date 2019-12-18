package com.gabrieldchartier.compendia.persistence.authentication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gabrieldchartier.compendia.models.AuthToken

@Dao
interface AuthTokenDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(authenticationToken: AuthToken): Long

    @Query("UPDATE authentication_token SET token = null WHERE account_pk = :pk")
    fun nullifyToken(pk: Int): Int

    @Query("SELECT * FROM authentication_token WHERE account_pk = :pk")
    suspend fun searchByPk(pk: Int): AuthToken?
}