package com.gabrieldchartier.compendia.api.main.network_responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CollectionDetailsResponse (

        @SerializedName("number_of_collected_comics")
        @Expose
        var numberOfCollectedComics: Int,

        @SerializedName("number_of_read_comics")
        @Expose
        var numberOfReadComics: Int,

        @SerializedName("number_of_reviews")
        @Expose
        var numberOfReviews: Int
)