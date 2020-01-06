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

@Entity(tableName = "comic_creator_join", primaryKeys = ["pk", "pk"],
        foreignKeys = [
                ForeignKey(entity = Comic::class, parentColumns = ["pk"], childColumns = ["comic_pk"]),
                ForeignKey(entity = Creator::class, parentColumns = ["pk"], childColumns = ["creator_pk"])
        ]
)
data class ComicCreatorJoin(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name="pk")
        var pk: Int,

        @ColumnInfo(name = "comic_id")
        val comicID: Int,

        @ColumnInfo(name = "creator_id")
        val creatorID: Int,

        @ColumnInfo(name = "creator_type")
        val creatorType: String?
)