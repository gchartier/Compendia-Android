package com.gabrieldchartier.compendia.persistence.main

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gabrieldchartier.compendia.models.Comic
import com.gabrieldchartier.compendia.models.ComicCreator
import com.gabrieldchartier.compendia.models.ComicCreatorJoin
import com.gabrieldchartier.compendia.models.Creator

@Dao
interface ComicDAO : PublisherDAO, SeriesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComicsAndReplace(comics: List<Comic>?): List<Long>

    @Query("SELECT * FROM creators INNER JOIN comic_creator_join" +
            " ON creators.pk = comic_creator_join.creator_id" +
            " WHERE comic_creator_join.comic_id = :comicID")
    fun getComicCreators(comicID: Int): List<ComicCreator>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComicCreatorsAndReplace(comicCreators: List<ComicCreatorJoin>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCreatorsAndReplace(creators: List<Creator>?)
}