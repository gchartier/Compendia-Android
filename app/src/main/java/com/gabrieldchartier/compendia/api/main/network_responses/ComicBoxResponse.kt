package com.gabrieldchartier.compendia.api.main.network_responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ComicBoxResponse (
        @SerializedName("pk")
        @Expose
        var pk: Int,

        @SerializedName("name")
        @Expose
        var name: String,

        @SerializedName("number_of_entries")
        @Expose
        var numberOfEntries: Int
)