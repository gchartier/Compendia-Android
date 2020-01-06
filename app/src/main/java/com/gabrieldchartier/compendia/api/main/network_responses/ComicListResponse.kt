package com.gabrieldchartier.compendia.api.main.network_responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ComicListResponse(
        @SerializedName("results")
        @Expose
        var results: List<ComicResponse>,

        @SerializedName("detail")
        @Expose
        var detail: String
) {
    override fun toString(): String {
        return "ComicListResponse(results=$results, detail='$detail')"
    }
}