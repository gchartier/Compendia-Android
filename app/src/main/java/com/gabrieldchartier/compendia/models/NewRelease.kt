package com.gabrieldchartier.compendia.models

import androidx.room.*

@Entity(
        tableName = "new_releases",
        foreignKeys = [
                ForeignKey(
                        entity = Comic::class,
                        parentColumns = ["pk"],
                        childColumns = ["comic_pk"],
                        onDelete = ForeignKey.CASCADE
                )
        ]
)
data class NewRelease (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name="pk")
        var pk: Int,

        @ColumnInfo(name = "comic_pk", index = true)
        var comicPK: Int
)