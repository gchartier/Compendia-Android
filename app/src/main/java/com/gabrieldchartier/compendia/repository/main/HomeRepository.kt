package com.gabrieldchartier.compendia.repository.main

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.gabrieldchartier.compendia.api.GenericResponse
import com.gabrieldchartier.compendia.api.main.CompendiaAPIMainService
import com.gabrieldchartier.compendia.api.main.network_responses.CollectionDetailsResponse
import com.gabrieldchartier.compendia.api.main.network_responses.ComicBoxListResponse
import com.gabrieldchartier.compendia.api.main.network_responses.ComicListResponse
import com.gabrieldchartier.compendia.models.*
import com.gabrieldchartier.compendia.persistence.ComicPersistenceHelper
import com.gabrieldchartier.compendia.persistence.authentication.AccountPropertiesDAO
import com.gabrieldchartier.compendia.persistence.main.CollectionDAO
import com.gabrieldchartier.compendia.persistence.main.ComicBoxDAO
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
import com.gabrieldchartier.compendia.util.DateUtilities.Companion.convertServerStringDateToLong
import com.gabrieldchartier.compendia.util.DateUtilities.Companion.getCurrentReleaseWeek
import com.gabrieldchartier.compendia.util.GenericAPIResponse
import com.gabrieldchartier.compendia.util.GenericAPIResponse.APISuccessResponse
import com.gabrieldchartier.compendia.util.PreferenceKeys
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.collections.ArrayList

class HomeRepository
@Inject
constructor(
        val compendiaAPIMainService: CompendiaAPIMainService,
        val accountPropertiesDAO: AccountPropertiesDAO,
        val newReleasesDAO: NewReleasesDAO,
        val comicBoxDAO: ComicBoxDAO,
        val collectionDAO: CollectionDAO,
        val sessionManager: SessionManager,
        val sharedPreferences: SharedPreferences,
        val sharedPrefsEditor: SharedPreferences.Editor
): JobManager("HomeRepository") {

    fun attemptGetAccountProperties(authToken: AuthToken): LiveData<DataState<HomeViewState>> {
        return object: NetworkBoundResource<AccountProperties, HomeViewState, AccountProperties>(
                sessionManager.isConnectedToInternet(),
                isNetworkRequest = false,
                shouldLoadFromCache = true,
                shouldCancelIfNoInternet = false
        )
        {
            override suspend fun createCacheRequestAndReturn() {
                withContext(Main) {
                    result.addSource(loadFromCache()) { viewState ->
                        onCompleteJob(DataState.data(data = viewState, response = null))
                    }
                }
            }

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

            override fun setJob(job: Job) {
                addJob("attemptGetAccountProperties", job)
            }

            // Methods not used in this case
            override suspend fun handleAPISuccessResponse(response: APISuccessResponse<AccountProperties>) { }
            override fun createCall(): LiveData<GenericAPIResponse<AccountProperties>> { return AbsentLiveData.create() }
            override suspend fun updateLocalDB(cacheObject: AccountProperties?) { }
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
        return object: NetworkBoundResource<ComicListResponse, HomeViewState, List<ComicDataWrapper>>(
                sessionManager.isConnectedToInternet(),
                isNetworkRequest = true,
                shouldLoadFromCache = true,
                shouldCancelIfNoInternet = false
        )
        {
            override fun loadFromCache(): LiveData<HomeViewState> {

                return newReleasesDAO.getNewReleases(sharedPreferences.getLong(PreferenceKeys.CURRENT_RELEASE_WEEK, getCurrentReleaseWeek()))
                        .switchMap { newReleases ->
                            object: LiveData<HomeViewState>() {
                                override fun onActive() {
                                    super.onActive()
                                    value = HomeViewState(homeFields = HomeViewState.HomeFields(newReleases = newReleases))
                                    Log.d("HomeRepository", "onActive (line 153): ${value.toString()}")
                                }
                            }
                        }
            }

            override suspend fun updateLocalDB(cacheObject: List<ComicDataWrapper>?) {
                Log.d("HomeRepository", "updateLocalDB (line 157): ${cacheObject.toString()}")
                if(cacheObject != null)
                    ComicPersistenceHelper.insertComicDataToDb(newReleasesDAO, cacheObject)
            }

            override suspend fun createCacheRequestAndReturn() {
                withContext(Main) {
                    result.addSource(loadFromCache()) { viewState ->
                        onCompleteJob(DataState.data(data = viewState, response = null))
                    }
                }
            }

            override suspend fun handleAPISuccessResponse(response: APISuccessResponse<ComicListResponse>) {
                val comicWrapperList: ArrayList<ComicDataWrapper> = ComicPersistenceHelper.createComicWrapperList(response)

                Log.d("HomeRepository", "handleAPISuccessResponse (line 166): is the response results empty? ${response.body.results.isEmpty()}")

                if (response.body.results.isEmpty())
                    sharedPrefsEditor.putLong(PreferenceKeys.CURRENT_RELEASE_WEEK, getCurrentReleaseWeek())
                else
                    sharedPrefsEditor.putLong(PreferenceKeys.CURRENT_RELEASE_WEEK, convertServerStringDateToLong(response.body.results[0].releaseDate))
                sharedPrefsEditor.apply()

                updateLocalDB(comicWrapperList)
                createCacheRequestAndReturn()
            }

            override fun createCall(): LiveData<GenericAPIResponse<ComicListResponse>> {
                return compendiaAPIMainService.getNewReleases("Token ${authToken.token}")
            }

            override fun setJob(job: Job) {
                addJob("attemptGetNewReleases", job)
            }
        }.asLiveData()
    }

    fun attemptGetComicBoxes(authToken: AuthToken): LiveData<DataState<HomeViewState>> {
        return object: NetworkBoundResource<ComicBoxListResponse, HomeViewState, List<ComicBox>>(
                sessionManager.isConnectedToInternet(),
                isNetworkRequest = true,
                shouldLoadFromCache = true,
                shouldCancelIfNoInternet = false
        )
        {
            override fun loadFromCache(): LiveData<HomeViewState> {

                return comicBoxDAO.getComicBoxes()
                        .switchMap { comicBoxes ->
                            object: LiveData<HomeViewState>() {
                                override fun onActive() {
                                    super.onActive()
                                    value = HomeViewState(homeFields = HomeViewState.HomeFields(comicBoxes = comicBoxes))
                                }
                            }
                        }
            }

            override suspend fun updateLocalDB(cacheObject: List<ComicBox>?) {
                if(cacheObject != null)
                    comicBoxDAO.insertComicBoxesAndReplace(cacheObject)
            }

            override suspend fun createCacheRequestAndReturn() {
                withContext(Main) {
                    result.addSource(loadFromCache()) { viewState ->
                        onCompleteJob(DataState.data(data = viewState, response = null))
                    }
                }
            }

            override suspend fun handleAPISuccessResponse(response: APISuccessResponse<ComicBoxListResponse>) {
                val comicBoxes: ArrayList<ComicBox> = ArrayList()
                for(box in response.body.results)
                    comicBoxes.add(ComicBox(pk = box.pk, name = box.name))
                updateLocalDB(comicBoxes)
                createCacheRequestAndReturn()
            }

            override fun createCall(): LiveData<GenericAPIResponse<ComicBoxListResponse>> {
                return compendiaAPIMainService.getComicBoxes("Token ${authToken.token}")
            }

            override fun setJob(job: Job) {
                addJob("attemptGetComicBoxes", job)
            }
        }.asLiveData()
    }

    fun attemptGetCollectionDetails(authToken: AuthToken): LiveData<DataState<HomeViewState>> {
        return object: NetworkBoundResource<CollectionDetailsResponse, HomeViewState, CollectionDetails>(
                sessionManager.isConnectedToInternet(),
                isNetworkRequest = true,
                shouldLoadFromCache = true,
                shouldCancelIfNoInternet = false
        )
        {
            override fun loadFromCache(): LiveData<HomeViewState> {

                return collectionDAO.getCollectionDetails()
                        .switchMap { collectionDetails ->
                            object: LiveData<HomeViewState>() {
                                override fun onActive() {
                                    super.onActive()
                                    value = HomeViewState(homeFields = HomeViewState.HomeFields(collectionDetails = collectionDetails))
                                }
                            }
                        }
            }

            override suspend fun updateLocalDB(cacheObject: CollectionDetails?) {
                if(cacheObject != null)
                    collectionDAO.insertOrReplaceCollectionDetails(cacheObject)
            }

            override suspend fun createCacheRequestAndReturn() {
                withContext(Main) {
                    result.addSource(loadFromCache()) { viewState ->
                        onCompleteJob(DataState.data(data = viewState, response = null))
                    }
                }
            }

            override suspend fun handleAPISuccessResponse(response: APISuccessResponse<CollectionDetailsResponse>) {
                updateLocalDB(
                        CollectionDetails(
                                pk = null,
                                numberOfCollectedComics = response.body.numberOfCollectedComics,
                                numberOfReadComics = response.body.numberOfReadComics,
                                numberOfReviews = response.body.numberOfReviews
                        )
                )
                createCacheRequestAndReturn()
            }

            override fun createCall(): LiveData<GenericAPIResponse<CollectionDetailsResponse>> {
                return compendiaAPIMainService.getCollectionDetails("Token ${authToken.token}")
            }

            override fun setJob(job: Job) {
                addJob("attemptGetCollectionDetails", job)
            }
        }.asLiveData()
    }
}