package com.gabrieldchartier.compendia.persistence.main

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gabrieldchartier.compendia.models.CollectionDetails

@Dao
interface CollectionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplaceCollectionDetails(collectionDetails: CollectionDetails)

    @Query("SELECT * FROM collection_details")
    fun getCollectionDetails(): LiveData<CollectionDetails?>
}