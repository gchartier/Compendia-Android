package com.gabrieldchartier.compendia.repository.main

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.gabrieldchartier.compendia.api.GenericResponse
import com.gabrieldchartier.compendia.api.main.CompendiaAPIMainService
import com.gabrieldchartier.compendia.api.main.network_responses.ComicListResponse
import com.gabrieldchartier.compendia.models.AccountProperties
import com.gabrieldchartier.compendia.models.AuthToken
import com.gabrieldchartier.compendia.models.Comic
import com.gabrieldchartier.compendia.models.NewRelease
import com.gabrieldchartier.compendia.persistence.authentication.AccountPropertiesDAO
import com.gabrieldchartier.compendia.persistence.main.NewReleasesDAO
import com.gabrieldchartier.compendia.repository.JobManager
import com.gabrieldchartier.compendia.repository.NetworkBoundResource
import com.gabrieldchartier.compendia.session.SessionManager
import com.gabrieldchartier.compendia.ui.DataState
import com.gabrieldchartier.compendia.ui.Response
import com.gabrieldchartier.compendia.ui.ResponseType
import com.gabrieldchartier.compendia.ui.main.home.state.HomeViewState
import com.gabrieldchartier.compendia.util.AbsentLiveData
import com.gabrieldchartier.compendia.util.DateUtilities
import com.gabrieldchartier.compendia.util.DateUtilities.Companion.convertServerStringDateToDate
import com.gabrieldchartier.compendia.util.DateUtilities.Companion.convertServerStringDateToLong
import com.gabrieldchartier.compendia.util.GenericAPIResponse
import com.gabrieldchartier.compendia.util.GenericAPIResponse.APISuccessResponse
import com.gabrieldchartier.compendia.util.PreferenceKeys
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import org.androidannotations.annotations.sharedpreferences.Pref
import java.time.LocalDate
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class HomeRepository
@Inject
constructor(
        val compendiaAPIMainService: CompendiaAPIMainService,
        val accountPropertiesDAO: AccountPropertiesDAO,
        val newReleasesDAO: NewReleasesDAO,
        val sessionManager: SessionManager,
        val sharedPreferences: SharedPreferences,
        val sharedPrefsEditor: SharedPreferences.Editor
): JobManager("HomeRepository") {

    fun attemptGetAccountProperties(authToken: AuthToken): LiveData<DataState<HomeViewState>> {
        return object: NetworkBoundResource<AccountProperties, HomeViewState, AccountProperties>(
                sessionManager.isConnectedToInternet(),
                isNetworkRequest = true,
                shouldLoadFromCache = true,
                shouldCancelIfNoInternet = false
        )
        {
            override fun loadFromCache(): LiveData<HomeViewState> {
                return accountPropertiesDAO.searchByPK(authToken.account_pk!!)
                        .switchMap { accountProperties ->
                            object: LiveData<HomeViewState>() {
                                override fun onActive() {
                                    super.onActive()
                                    value = HomeViewState(accountProperties)
                                }
                            }
                        }
            }

            override suspend fun updateLocalDB(cacheObject: AccountProperties?) {
                cacheObject?.let {
                    accountPropertiesDAO.updateAccountProperties(it.username, it.pk)
                }
            }

            override suspend fun createCacheRequestAndReturn() {
                withContext(Main) {
                    result.addSource(loadFromCache()) { viewState ->
                        onCompleteJob(DataState.data(data = viewState, response = null))
                    }
                }
            }

            override suspend fun handleAPISuccessResponse(response: APISuccessResponse<AccountProperties>) {
                updateLocalDB(response.body)
                createCacheRequestAndReturn()
            }

            override fun createCall(): LiveData<GenericAPIResponse<AccountProperties>> {
                return compendiaAPIMainService.getAccountProperties("Token ${authToken.token}")
            }

            override fun setJob(job: Job) {
                addJob("attemptGetAccountProperties", job)
            }

        }.asLiveData()
    }

    fun attemptChangePassword(
            authToken: AuthToken,
            currentPassword: String,
            newPassword: String,
            newPasswordConfirmation: String): LiveData<DataState<HomeViewState>>
    {
        return object: NetworkBoundResource<GenericResponse, HomeViewState, Any>(
                sessionManager.isConnectedToInternet(),
                isNetworkRequest = true,
                shouldLoadFromCache = false,
                shouldCancelIfNoInternet = true
        )
        {
            override suspend fun handleAPISuccessResponse(response: APISuccessResponse<GenericResponse>) {
                withContext(Main) {
                    onCompleteJob(DataState.data(data = null,
                            response = Response(response.body.response, ResponseType.Toast())))
                }
            }

            override fun createCall(): LiveData<GenericAPIResponse<GenericResponse>> {
                return compendiaAPIMainService.changePassword("Token ${authToken.token}",
                        currentPassword, newPassword, newPasswordConfirmation)
            }

            override fun setJob(job: Job) {
                addJob("attemptChangePassword", job)
            }

            // Cache functionality is not used for this request
            override fun loadFromCache(): LiveData<HomeViewState> { return AbsentLiveData.create() }
            override suspend fun updateLocalDB(cacheObject: Any?) { }
            override suspend fun createCacheRequestAndReturn() { }

        }.asLiveData()
    }

    fun attemptGetNewReleases(authToken: AuthToken): LiveData<DataState<HomeViewState>> {
        return object: NetworkBoundResource<ComicListResponse, HomeViewState, List<Comic>>(
                sessionManager.isConnectedToInternet(),
                isNetworkRequest = true,
                shouldLoadFromCache = true,
                shouldCancelIfNoInternet = false
        )
        {
            override fun loadFromCache(): LiveData<HomeViewState> {

                return newReleasesDAO.getNewReleases(sharedPreferences.getLong(PreferenceKeys.CURRENT_RELEASE_WEEK, DateUtilities.getCurrentReleaseWeek()))
                        .switchMap { newReleases ->
                            object: LiveData<HomeViewState>() {
                                override fun onActive() {
                                    super.onActive()
                                    value = HomeViewState(homeFields = HomeViewState.HomeFields(newReleases = newReleases))
                                }
                            }
                        }
            }

            override suspend fun updateLocalDB(cacheObject: List<Comic>?) {
                val newReleases: ArrayList<NewRelease> = ArrayList()

                if(cacheObject != null)
                    for(comic in cacheObject)
                        newReleases.add(NewRelease(pk=0, comicPK = comic.pk))

                cacheObject?.let {
                    newReleasesDAO.insertNewReleaseComicsOrIgnore(cacheObject)
                }
            }

            override suspend fun createCacheRequestAndReturn() {
                withContext(Main) {
                    result.addSource(loadFromCache()) { viewState ->
                        onCompleteJob(DataState.data(data = viewState, response = null))
                    }
                }
            }

            override suspend fun handleAPISuccessResponse(response: APISuccessResponse<ComicListResponse>) {
                val comicList: ArrayList<Comic> = ArrayList()
                for(newReleaseResponse in response.body.results) {
                    comicList.add(
                            Comic(
                                    pk = newReleaseResponse.pk,
                                    title = newReleaseResponse.title,
                                    itemNumber = newReleaseResponse.itemNumber,
                                    releaseDate = convertServerStringDateToLong(newReleaseResponse.releaseDate),
                                    coverPrice = newReleaseResponse.coverPrice,
                                    cover = newReleaseResponse.cover,
                                    description = newReleaseResponse.description,
                                    pageCount = newReleaseResponse.pageCount,
                                    publisher_pk = newReleaseResponse.publisher_pk,
                                    series_pk = newReleaseResponse.series_pk,
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
                                        if(it != null) convertServerStringDateToLong(it) else null
                                    },
                                    purchasePrice = newReleaseResponse.collectionDetails?.purchasePrice,
                                    boughtAt = newReleaseResponse.collectionDetails?.boughtAt,
                                    condition = newReleaseResponse.collectionDetails?.condition,
                                    isSlabbed = newReleaseResponse.collectionDetails?.isSlabbed,
                                    certification = newReleaseResponse.collectionDetails?.certification,
                                    grade = newReleaseResponse.collectionDetails?.grade,
                                    quantity = newReleaseResponse.collectionDetails?.quantity
                            )
                    )
                }
                sharedPrefsEditor.putLong(PreferenceKeys.CURRENT_RELEASE_WEEK, convertServerStringDateToLong(response.body.results[0].releaseDate))
                sharedPrefsEditor.apply()
                updateLocalDB(comicList)
                createCacheRequestAndReturn()
            }

            override fun createCall(): LiveData<GenericAPIResponse<ComicListResponse>> {
                return compendiaAPIMainService.getNewReleases("Token ${authToken.token}")
            }

            override fun setJob(job: Job) {
                addJob("attemptGetNewReleaseCovers", job)
            }
        }.asLiveData()
    }
}