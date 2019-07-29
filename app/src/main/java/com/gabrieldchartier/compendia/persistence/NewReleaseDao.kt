package com.gabrieldchartier.compendia.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gabrieldchartier.compendia.models.Comic

@Dao
interface NewReleaseDao
{
    @Insert
    fun insertNewReleases(vararg comic: Comic?): LongArray

    @Query("SELECT * FROM comics")
    fun getNewReleases(): LiveData<List<Comic>>

    @Delete
    fun delete(vararg comic: Comic?): Int

    @Query("DELETE FROM comics")
    fun nukeTable()

    @Update
    fun update(vararg comic: Comic?): Int
}