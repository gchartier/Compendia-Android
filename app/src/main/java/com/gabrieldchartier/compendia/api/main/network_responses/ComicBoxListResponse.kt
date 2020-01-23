package com.gabrieldchartier.compendia.api.main.network_responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ComicBoxListResponse(
        @SerializedName("results")
        @Expose
        var results: List<ComicBoxResponse>,

        @SerializedName("detail")
        @Expose
        var detail: String
) {
    override fun toString(): String {
        return "ComicBoxListResponse(results=$results, detail='$detail')"
    }
}