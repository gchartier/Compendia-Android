package com.gabrieldchartier.compendia.models

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "comics",
        foreignKeys = [
                ForeignKey(entity = Publisher::class, parentColumns = ["pk"],
                        childColumns = ["publisher_pk"], onDelete = CASCADE),

                ForeignKey(entity = Series::class, parentColumns = ["pk"],
                        childColumns = ["series_pk"], onDelete = CASCADE)
        ]
)
data class Comic (
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name="pk")
        var pk: Int,

        @ColumnInfo(name="title")
        var title: String,

        @ColumnInfo(name="item_number")
        var itemNumber: String,

        @ColumnInfo(name="release_date")
        var releaseDate: Long?,

        @ColumnInfo(name="cover_price")
        var coverPrice: String?,

        @ColumnInfo(name="cover")
        var cover: String,

        @ColumnInfo(name="description")
        var description: String?,

        @ColumnInfo(name="page_count")
        var pageCount: Int?,

        @ColumnInfo(name="publisher_pk", index = true)
        var publisher_pk: Int,

        @ColumnInfo(name="series_pk", index = true)
        var series_pk: Int,

        @ColumnInfo(name="barcode")
        var barcode: String?,

        @ColumnInfo(name="printing")
        var printing: Int? = 1,

        @ColumnInfo(name="format_type")
        var formatType: String?,

        @ColumnInfo(name="is_mature")
        var isMature: Boolean,

        @ColumnInfo(name="version_of")
        var versionOf: Int?,

        @ColumnInfo(name="versions")
        var versions: Int,

        @ColumnInfo(name="variant_code")
        var variantCode: String?,

        @ColumnInfo(name="total_wanted")
        var totalWanted: Int? = 0,

        @ColumnInfo(name="total_favorited")
        var totalFavorited: Int? = 0,

        @ColumnInfo(name="total_owned")
        var totalOwned: Int? = 0,

        @ColumnInfo(name="total_read")
        var totalRead: Int? = 0,

        @ColumnInfo(name="avg_rating")
        var avgRating: String,

        @ColumnInfo(name="number_of_reviews")
        var numberOfReviews: Int,

        @ColumnInfo(name="is_collected")
        var isCollected: Boolean,

        @ColumnInfo(name="date_collected")
        var dateCollected: Long?,

        @ColumnInfo(name="purchase_price")
        var purchasePrice: String?,

        @ColumnInfo(name="bought_at")
        var boughtAt: String?,

        @ColumnInfo(name="condition")
        var condition: String?,

        @ColumnInfo(name="is_slabbed")
        var isSlabbed: Boolean? = false,

        @ColumnInfo(name="certification")
        var certification: String?,

        @ColumnInfo(name="grade")
        var grade: String?,

        @ColumnInfo(name="quantity")
        var quantity: Int? = 1
)

data class ComicDataWrapper (var comic: Comic, var series: Series, var publisher: Publisher, var creators: List<ComicCreator>?)

data class ComicWithData(
        @Embedded val comic: Comic,
        @ColumnInfo(name="publisher_name")
        val publisherName: String,
        @ColumnInfo(name="series_name")
        val seriesName: String
)