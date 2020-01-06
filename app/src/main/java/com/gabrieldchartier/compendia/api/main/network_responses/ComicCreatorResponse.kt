package com.gabrieldchartier.compendia.api.main.network_responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ComicCreatorResponse (
        @SerializedName("pk")
        @Expose
        var pk: Int,

        @SerializedName("creator_id")
        @Expose
        var creatorID: Int,

        @SerializedName("name")
        @Expose
        var name: String,

        @SerializedName("creator_type")
        @Expose
        var creatorType: String?
)