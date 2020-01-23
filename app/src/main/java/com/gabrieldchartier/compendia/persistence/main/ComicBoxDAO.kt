package com.gabrieldchartier.compendia.persistence.main

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gabrieldchartier.compendia.models.ComicBox

@Dao
interface ComicBoxDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComicBoxesAndReplace(boxes: List<ComicBox>): List<Long>?

    @Query("SELECT * FROM comic_boxes")
    fun getComicBoxes(): LiveData<List<ComicBox>?>
}