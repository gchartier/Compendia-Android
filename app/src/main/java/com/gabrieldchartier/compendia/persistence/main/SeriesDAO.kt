package com.gabrieldchartier.compendia.persistence.main

import androidx.room.*
import com.gabrieldchartier.compendia.models.Series


@Dao
interface SeriesDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(series: List<Series>?)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(series: List<Series>?)

    @Transaction
    fun insertOrUpdateSeries(series: List<Series>?) {
        insert(series)
        update(series)
    }
}