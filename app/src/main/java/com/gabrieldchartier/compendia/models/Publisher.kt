package com.gabrieldchartier.compendia.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "publishers")
data class Publisher (
        @SerializedName("pk")
        @Expose
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name="pk")
        var pk: Int,

        @SerializedName("name")
        @Expose
        @ColumnInfo(name="name")
        var name: String
)