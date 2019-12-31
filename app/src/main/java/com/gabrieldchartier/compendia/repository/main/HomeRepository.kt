package com.gabrieldchartier.compendia.repository.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.gabrieldchartier.compendia.api.GenericResponse
import com.gabrieldchartier.compendia.api.main.CompendiaAPIMainService
import com.gabrieldchartier.compendia.models.AccountProperties
import com.gabrieldchartier.compendia.models.AuthToken
import com.gabrieldchartier.compendia.persistence.authentication.AccountPropertiesDAO
import com.gabrieldchartier.compendia.repository.NetworkBoundResource
import com.gabrieldchartier.compendia.session.SessionManager
import com.gabrieldchartier.compendia.ui.DataState
import com.gabrieldchartier.compendia.ui.Response
import com.gabrieldchartier.compendia.ui.ResponseType
import com.gabrieldchartier.compendia.ui.main.home.state.ChangePasswordFields
import com.gabrieldchartier.compendia.ui.main.home.state.HomeViewState
import com.gabrieldchartier.compendia.util.AbsentLiveData
import com.gabrieldchartier.compendia.util.GenericAPIResponse
import com.gabrieldchartier.compendia.util.GenericAPIResponse.APISuccessResponse
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository
@Inject
constructor(
        val compendiaAPIMainService: CompendiaAPIMainService,
        val accountPropertiesDAO: AccountPropertiesDAO,
        val sessionManager: SessionManager
) {
    private var repositoryJob: Job? = null

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
                repositoryJob?.cancel()
                repositoryJob = job
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
                repositoryJob?.cancel()
                repositoryJob = job
            }

            // Cache functionality is not used for this request
            override fun loadFromCache(): LiveData<HomeViewState> { return AbsentLiveData.create() }
            override suspend fun updateLocalDB(cacheObject: Any?) { }
            override suspend fun createCacheRequestAndReturn() { }

        }.asLiveData()
    }

    fun cancelActiveJobs() {
        Log.d("HomeRepository", "cancelActiveJobs (line 20): Cancelling active jobs...")
    }
}