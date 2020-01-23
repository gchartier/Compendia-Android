package com.gabrieldchartier.compendia.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collection_details")
data class CollectionDetails (

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name="pk")
        var pk: Int?,

        @ColumnInfo(name="number_of_collected_comics")
        var numberOfCollectedComics: Int,

        @ColumnInfo(name="number_of_read_comics")
        var numberOfReadComics: Int,

        @ColumnInfo(name="number_of_reviews")
        var numberOfReviews: Int
)