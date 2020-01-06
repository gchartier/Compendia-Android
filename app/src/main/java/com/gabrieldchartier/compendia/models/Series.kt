package com.gabrieldchartier.compendia.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "series")
data class Series (
        @SerializedName("pk")
        @Expose
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name="pk")
        var pk: Int,

        @SerializedName("name")
        @Expose
        @ColumnInfo(name="name")
        var name: String,

        @SerializedName("publisher")
        @Expose
        @ColumnInfo(name="publisher_pk", index = true)
        var publisher_pk: Int,

        @SerializedName("genre")
        @Expose
        @ColumnInfo(name="genre")
        var genre: String?,

        @SerializedName("years")
        @Expose
        @ColumnInfo(name="years")
        var years: String?,

        @SerializedName("number_of_entries")
        @Expose
        @ColumnInfo(name="number_of_entries")
        var numberOfEntries: Int?,

        @SerializedName("is_one_shot")
        @Expose
        @ColumnInfo(name="is_one_shot")
        var isOneShot: Boolean? = false,

        @SerializedName("is_mini_series")
        @Expose
        @ColumnInfo(name="is_mini_series")
        var isMiniSeries: Boolean? = false
)