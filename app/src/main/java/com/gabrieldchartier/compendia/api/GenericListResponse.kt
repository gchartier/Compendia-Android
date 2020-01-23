package com.gabrieldchartier.compendia.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

abstract class GenericListResponse<ObjectType>(
        @SerializedName("results")
        @Expose
        var results: List<ObjectType>,

        @SerializedName("detail")
        @Expose
        var detail: String
)
{
        override fun toString(): String {
                return "ListResponse(results=$results, detail='$detail')"
        }
}