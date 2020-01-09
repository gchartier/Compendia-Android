package com.gabrieldchartier.compendia.persistence

import com.gabrieldchartier.compendia.api.main.network_responses.ComicListResponse
import com.gabrieldchartier.compendia.models.Comic
import com.gabrieldchartier.compendia.models.ComicSeriesPublisherWrapper
import com.gabrieldchartier.compendia.models.Publisher
import com.gabrieldchartier.compendia.models.Series
import com.gabrieldchartier.compendia.persistence.main.ComicDAO
import com.gabrieldchartier.compendia.util.DateUtilities
import com.gabrieldchartier.compendia.util.GenericAPIResponse

class ComicPersistenceHelper {
    companion object {

        fun insertComicData(comicDAO: ComicDAO, responseData: List<ComicSeriesPublisherWrapper>) {
            val newReleases: ArrayList<Comic>     = ArrayList()
            val series:      ArrayList<Series>    = ArrayList()
            val publishers:  ArrayList<Publisher> = ArrayList()

            for(comicWrapper in responseData) {
                newReleases.add(comicWrapper.comic)
                series.add(comicWrapper.series)
                publishers.add(comicWrapper.publisher)
            }

            publishers.let {
                comicDAO.insertPublishersAndReplace(it)
            }

            series.let {
                comicDAO.insertSeriesAndReplace(it)
            }

            newReleases.let {
                comicDAO.insertComicsAndReplace(it)
            }
        }

        fun createComicWrapperList(apiResponse: GenericAPIResponse.APISuccessResponse<ComicListResponse>): ArrayList<ComicSeriesPublisherWrapper> {
            val comicWrapperList: ArrayList<ComicSeriesPublisherWrapper> = ArrayList()
            for(newReleaseResponse in apiResponse.body.results) {
                comicWrapperList.add(
                        ComicSeriesPublisherWrapper(
                                comic = Comic(
                                        pk = newReleaseResponse.pk,
                                        title = newReleaseResponse.title,
                                        itemNumber = newReleaseResponse.itemNumber,
                                        releaseDate = DateUtilities.convertServerStringDateToLong(newReleaseResponse.releaseDate),
                                        coverPrice = newReleaseResponse.coverPrice,
                                        cover = newReleaseResponse.cover,
                                        description = newReleaseResponse.description,
                                        pageCount = newReleaseResponse.pageCount,
                                        publisher_pk = newReleaseResponse.publisher.id,
                                        series_pk = newReleaseResponse.series.id,
                                        barcode = newReleaseResponse.barcode,
                                        printing = newReleaseResponse.printing,
                                        formatType = newReleaseResponse.formatType,
                                        isMature = newReleaseResponse.isMature,
                                        versionOf = newReleaseResponse.versionOf,
                                        versions = newReleaseResponse.versions,
                                        variantCode = newReleaseResponse.variantCode,
                                        totalWanted = newReleaseResponse.totalWanted,
                                        totalFavorited = newReleaseResponse.totalFavorited,
                                        totalOwned = newReleaseResponse.totalOwned,
                                        totalRead = newReleaseResponse.totalRead,
                                        avgRating = newReleaseResponse.avgRating,
                                        numberOfReviews = newReleaseResponse.numberOfReviews,
                                        dateCollected = newReleaseResponse.collectionDetails?.dateCollected.let {
                                            if(it != null) DateUtilities.convertServerStringDateToLong(it) else null
                                        },
                                        purchasePrice = newReleaseResponse.collectionDetails?.purchasePrice,
                                        boughtAt = newReleaseResponse.collectionDetails?.boughtAt,
                                        condition = newReleaseResponse.collectionDetails?.condition,
                                        isSlabbed = newReleaseResponse.collectionDetails?.isSlabbed,
                                        certification = newReleaseResponse.collectionDetails?.certification,
                                        grade = newReleaseResponse.collectionDetails?.grade,
                                        quantity = newReleaseResponse.collectionDetails?.quantity
                                ),
                                series = Series(
                                        pk = newReleaseResponse.series.id,
                                        name = newReleaseResponse.series.name,
                                        genre = newReleaseResponse.series.genre,
                                        years = newReleaseResponse.series.years,
                                        isOneShot = newReleaseResponse.series.is_one_shot,
                                        isMiniSeries = newReleaseResponse.series.is_mini_series,
                                        miniSeriesLimit = newReleaseResponse.series.mini_series_limit,
                                        publisher_pk = newReleaseResponse.series.publisher_id
                                ),
                                publisher = Publisher(
                                        pk = newReleaseResponse.publisher.id,
                                        name = newReleaseResponse.publisher.name
                                )
                        )
                )
            }
            return comicWrapperList
        }
    }
}