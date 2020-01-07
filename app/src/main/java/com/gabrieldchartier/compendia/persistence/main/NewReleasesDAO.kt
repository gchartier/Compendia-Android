package com.gabrieldchartier.compendia.persistence.main

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.gabrieldchartier.compendia.models.Comic

@Dao
interface NewReleasesDAO : ComicDAO {

    @Query("SELECT * FROM comics WHERE release_date = :currentReleaseDate")
    fun getNewReleases(currentReleaseDate: Long): LiveData<List<Comic>?>
}