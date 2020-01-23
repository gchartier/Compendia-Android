package com.gabrieldchartier.compendia.persistence

import com.gabrieldchartier.compendia.api.main.network_responses.ComicListResponse
import com.gabrieldchartier.compendia.models.*
import com.gabrieldchartier.compendia.persistence.main.ComicDAO
import com.gabrieldchartier.compendia.util.DateUtilities
import com.gabrieldchartier.compendia.util.GenericAPIResponse

class ComicPersistenceHelper {
    companion object {

        fun insertComicDataToDb(comicDAO: ComicDAO, responseData: List<ComicDataWrapper>) {
            val newReleasesToInsert:  ArrayList<Comic>            = ArrayList()
            val seriesToInsert:       ArrayList<Series>           = ArrayList()
            val publishersToInsert:   ArrayList<Publisher>        = ArrayList()
            val creatorsToInsert:     ArrayList<Creator>          = ArrayList()
            val creatorJoinsToInsert: ArrayList<ComicCreatorJoin> = ArrayList()

            for(comicWrapper in responseData) {

                newReleasesToInsert.add(comicWrapper.comic)
                seriesToInsert.add(comicWrapper.series)
                publishersToInsert.add(comicWrapper.publisher)

                comicWrapper.creators?.let {comicCreators ->
                    for (creator in comicCreators) {
                        creatorsToInsert.add(
                                Creator(
                                        pk = creator.creator_id,
                                        name = creator.name
                                )
                        )

                        creatorJoinsToInsert.add(
                                ComicCreatorJoin(
                                        pk = creator.pk,
                                        comicID = comicWrapper.comic.pk,
                                        creatorID = creator.creator_id,
                                        creatorType = creator.creator_type
                                )
                        )
                    }
                }
            }

            publishersToInsert.let {
                comicDAO.insertPublishersOrIgnore(it)
            }

            seriesToInsert.let {
                comicDAO.insertOrUpdateSeries(it)
            }

            creatorsToInsert.let {
                comicDAO.insertCreatorsAndReplace(it)
            }

            newReleasesToInsert.let {
                comicDAO.insertComicsAndReplace(it)
            }

            creatorJoinsToInsert.let {
                comicDAO.insertComicCreatorsAndReplace(it)
            }
        }

        fun createComicWrapperList(apiResponse: GenericAPIResponse.APISuccessResponse<ComicListResponse>): ArrayList<ComicDataWrapper> {
            val comicWrapperList: ArrayList<ComicDataWrapper> = ArrayList()
            var comicCreatorList: ArrayList<ComicCreator>

            for(newReleaseResponse in apiResponse.body.results) {

                comicCreatorList = ArrayList()

                newReleaseResponse.creators?.let { comicCreators ->
                    for(comicCreator in comicCreators)
                        comicCreatorList.add(
                                ComicCreator(
                                        pk = comicCreator.pk,
                                        comic_id = newReleaseResponse.pk,
                                        creator_id = comicCreator.creatorID,
                                        name = comicCreator.name,
                                        creator_type = comicCreator.creatorType
                                )
                        )
                }

                comicWrapperList.add(
                        ComicDataWrapper(
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
                                        isCollected = newReleaseResponse.collectionDetails != null,
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
                                ),
                                creators = comicCreatorList
                        )
                )
            }
            return comicWrapperList
        }
    }
}