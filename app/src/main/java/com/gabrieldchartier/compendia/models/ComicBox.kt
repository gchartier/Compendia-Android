package com.gabrieldchartier.compendia.models

import androidx.room.*

@Entity(tableName = "comic_boxes")
data class ComicBox(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name="pk")
        var pk: Int,

        @ColumnInfo(name="box_name")
        var name: String
)

@Entity(tableName = "comic_comic_box_join",
        foreignKeys = [
            ForeignKey(entity = Comic::class, parentColumns = ["pk"], childColumns = ["comic_id"]),
            ForeignKey(entity = ComicBox::class, parentColumns = ["pk"], childColumns = ["box_id"])
        ]
)
data class ComicComicBoxJoin(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name="pk")
        var pk: Int,

        @ColumnInfo(name = "comic_id", index = true)
        val comicID: Int,

        @ColumnInfo(name = "box_id", index = true)
        val boxID: Int
)

data class ComicBoxesForComic(
    @Relation(
            parentColumn = "comic_id",
            entityColumn = "box_id",
            associateBy = Junction(ComicComicBoxJoin::class)
    )
    val comicBoxes: List<ComicBox>?
)