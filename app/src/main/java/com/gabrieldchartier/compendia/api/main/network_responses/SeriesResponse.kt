package com.gabrieldchartier.compendia.api.main.network_responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SeriesResponse (
        @SerializedName("id")
        @Expose
        var id: Int,

        @SerializedName("name")
        @Expose
        var name: String,

        @SerializedName("genre")
        @Expose
        var genre: String?,

        @SerializedName("years")
        @Expose
        var years: String?,

        @SerializedName("is_one_shot")
        @Expose
        var is_one_shot: Boolean,

        @SerializedName("is_mini_series")
        @Expose
        var is_mini_series: Boolean,

        @SerializedName("mini_series_limit")
        @Expose
        var mini_series_limit: Int?,

        @SerializedName("publisher")
        @Expose
        var publisher_id: Int
)