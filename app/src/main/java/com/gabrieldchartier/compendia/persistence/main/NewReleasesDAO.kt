package com.gabrieldchartier.compendia.persistence.main

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.gabrieldchartier.compendia.models.ComicWithData

@Dao
interface NewReleasesDAO : ComicDAO {

    @Query("SELECT * FROM comics INNER JOIN publishers ON comics.publisher_pk = publishers.pk" +
            " INNER JOIN series ON comics.series_pk = series.pk" +
            " WHERE release_date = :currentReleaseDate")
    fun getNewReleases(currentReleaseDate: Long): LiveData<List<ComicWithData>?>
}