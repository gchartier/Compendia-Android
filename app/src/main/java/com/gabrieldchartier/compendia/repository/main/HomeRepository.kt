package com.gabrieldchartier.compendia.repository.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.gabrieldchartier.compendia.api.main.CompendiaAPIMainService
import com.gabrieldchartier.compendia.models.AccountProperties
import com.gabrieldchartier.compendia.models.AuthToken
import com.gabrieldchartier.compendia.persistence.authentication.AccountPropertiesDAO
import com.gabrieldchartier.compendia.repository.NetworkBoundResource
import com.gabrieldchartier.compendia.session.SessionManager
import com.gabrieldchartier.compendia.ui.DataState
import com.gabrieldchartier.compendia.ui.main.home.state.HomeViewState
import com.gabrieldchartier.compendia.util.GenericAPIResponse
import com.gabrieldchartier.compendia.util.GenericAPIResponse.APISuccessResponse
import kotlinx.coroutines.Job
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
                shouldLoadFromCache = true
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

            }

            override suspend fun handleAPISuccessResponse(response: APISuccessResponse<AccountProperties>) {

            }

            override fun createCall(): LiveData<GenericAPIResponse<AccountProperties>> {
                return compendiaAPIMainService.getAccountProperties("Token ${authToken.token}")
            }

            override fun setJob(job: Job) {
                repositoryJob?.cancel()
                repositoryJob = job
            }

            override suspend fun createCacheRequestAndReturn() {

            }

        }.asLiveData()
    }

    fun cancelActiveJobs() {
        Log.d("HomeRepository", "cancelActiveJobs (line 20): Cancelling active jobs...")
    }
}