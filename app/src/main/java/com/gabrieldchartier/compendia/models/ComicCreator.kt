package com.gabrieldchartier.compendia.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "creators")
data class Creator (
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name="pk")
        var pk: Int,

        @ColumnInfo(name="name")
        var name: String
)

@Entity(tableName = "comic_creator_join",
        foreignKeys = [
                ForeignKey(entity = Comic::class, parentColumns = ["pk"], childColumns = ["comic_id"]),
                ForeignKey(entity = Creator::class, parentColumns = ["pk"], childColumns = ["creator_id"])
        ]
)
data class ComicCreatorJoin(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name="pk")
        var pk: Int,

        @ColumnInfo(name = "comic_id", index = true)
        val comicID: Int,

        @ColumnInfo(name = "creator_id", index = true)
        val creatorID: Int,

        @ColumnInfo(name = "creator_type")
        val creatorType: String?
)

data class ComicCreator(
        var pk: Int,
        var comic_id: Int,
        var creator_id: Int,
        var creator_type: String?,
        var name: String
)