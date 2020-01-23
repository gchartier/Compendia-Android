package com.gabrieldchartier.compendia.persistence.main

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.gabrieldchartier.compendia.models.Publisher

@Dao
interface PublisherDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPublishersOrIgnore(publishers: List<Publisher>): List<Long>?
}
