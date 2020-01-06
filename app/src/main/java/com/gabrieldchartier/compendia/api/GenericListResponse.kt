package com.gabrieldchartier.compendia.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

abstract class GenericListResponse<ObjectType>(
        @SerializedName("count")
        @Expose
        var count: Int,

        @SerializedName("next")
        @Expose
        var next: String?,

        @SerializedName("previous")
        @Expose
        var previous: String?,

        @SerializedName("results")
        @Expose
        var results: List<ObjectType>?
)