package com.gabrieldchartier.compendia.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "series",
        foreignKeys = [
                ForeignKey(entity = Publisher::class, parentColumns = ["pk"],
                        childColumns = ["publisher_pk"], onDelete = ForeignKey.CASCADE)
        ])
data class Series (
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name="pk")
        var pk: Int,

        @ColumnInfo(name="name")
        var name: String,

        @ColumnInfo(name="genre")
        var genre: String?,

        @ColumnInfo(name="years")
        var years: String?,

        @ColumnInfo(name="is_one_shot")
        var isOneShot: Boolean? = false,

        @ColumnInfo(name="is_mini_series")
        var isMiniSeries: Boolean? = false,

        @ColumnInfo(name="mini_series_limit")
        var miniSeriesLimit: Int? = 0,

        @ColumnInfo(name="publisher_pk", index = true)
        var publisher_pk: Int
)