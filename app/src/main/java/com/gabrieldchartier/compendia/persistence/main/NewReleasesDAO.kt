package com.gabrieldchartier.compendia.persistence.main

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gabrieldchartier.compendia.models.Comic
import com.gabrieldchartier.compendia.models.NewRelease
import java.time.LocalDate

@Dao
interface NewReleasesDAO {
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    fun insertNewReleasesOrIgnore(newReleases: List<NewRelease>?): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNewReleaseComicsOrIgnore(newReleasesComics: List<Comic>?): List<Long>

    @Query("SELECT * FROM comics WHERE release_date = :currentReleaseDate")
    fun getNewReleases(currentReleaseDate: Long): LiveData<List<Comic>?>
}