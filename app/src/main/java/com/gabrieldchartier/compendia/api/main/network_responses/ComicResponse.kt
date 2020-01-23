package com.gabrieldchartier.compendia.api.main.network_responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ComicResponse(
        @SerializedName("id")
        @Expose
        var pk: Int,

        @SerializedName("title")
        @Expose
        var title: String,

        @SerializedName("item_number")
        @Expose
        var itemNumber: String,

        @SerializedName("release_date")
        @Expose
        var releaseDate: String,

        @SerializedName("cover_price")
        @Expose
        var coverPrice: String?,

        @SerializedName("cover")
        @Expose
        var cover: String,

        @SerializedName("description")
        @Expose
        var description: String?,

        @SerializedName("page_count")
        @Expose
        var pageCount: Int?,

        @SerializedName("publisher")
        @Expose
        var publisher: PublisherResponse,

        @SerializedName("series")
        @Expose
        var series: SeriesResponse,

        @SerializedName("creators")
        @Expose
        var creators: List<ComicCreatorResponse>?,

        @SerializedName("barcode")
        @Expose
        var barcode: String?,

        @SerializedName("printing")
        @Expose
        var printing: Int? = 1,

        @SerializedName("format_type")
        @Expose
        var formatType: String?,

        @SerializedName("is_mature")
        @Expose
        var isMature: Boolean,

        @SerializedName("version_of")
        @Expose
        var versionOf: Int?,

        @SerializedName("versions")
        @Expose
        var versions: Int,

        @SerializedName("variant_code")
        @Expose
        var variantCode: String?,

        @SerializedName("total_wanted")
        @Expose
        var totalWanted: Int? = 0,

        @SerializedName("total_favorited")
        @Expose
        var totalFavorited: Int? = 0,

        @SerializedName("total_owned")
        @Expose
        var totalOwned: Int? = 0,

        @SerializedName("total_read")
        @Expose
        var totalRead: Int? = 0,

        @SerializedName("avg_rating")
        @Expose
        var avgRating: String,

        @SerializedName("number_of_reviews")
        @Expose
        var numberOfReviews: Int,

        @SerializedName("collection_details")
        @Expose
        var collectionDetails: CollectionDetails?,

        @SerializedName("is_read")
        @Expose
        var isRead: Boolean,

        @SerializedName("is_favorited")
        @Expose
        var isFavorited: Boolean,

        @SerializedName("is_wanted")
        @Expose
        var isWanted: Boolean
)

class CollectionDetails (
        @SerializedName("id")
        @Expose
        var pk: Int,

        @SerializedName("date_collected")
        @Expose
        var dateCollected: String,

        @SerializedName("purchase_price")
        @Expose
        var purchasePrice: String?,

        @SerializedName("bought_at")
        @Expose
        var boughtAt: String?,

        @SerializedName("condition")
        @Expose
        var condition: String?,

        @SerializedName("is_slabbed")
        @Expose
        var isSlabbed: Boolean? = false,

        @SerializedName("certification")
        @Expose
        var certification: String?,

        @SerializedName("grade")
        @Expose
        var grade: String?,

        @SerializedName("quantity")
        @Expose
        var quantity: Int? = 1
)