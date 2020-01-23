package com.gabrieldchartier.compendia.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "publishers")
data class Publisher (
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name="pk")
        var pk: Int,

        @ColumnInfo(name="publisher_name")
        var name: String
)