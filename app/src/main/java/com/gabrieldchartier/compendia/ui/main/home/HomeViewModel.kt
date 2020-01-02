package com.gabrieldchartier.compendia.ui.main.home

import androidx.lifecycle.LiveData
import com.gabrieldchartier.compendia.models.AccountProperties
import com.gabrieldchartier.compendia.repository.main.HomeRepository
import com.gabrieldchartier.compendia.session.SessionManager
import com.gabrieldchartier.compendia.ui.BaseViewModel
import com.gabrieldchartier.compendia.ui.DataState
import com.gabrieldchartier.compendia.ui.authentication.state.AuthViewState
import com.gabrieldchartier.compendia.ui.main.home.state.ChangePasswordFields
import com.gabrieldchartier.compendia.ui.main.home.state.HomeStateEvent
import com.gabrieldchartier.compendia.ui.main.home.state.HomeStateEvent.*
import com.gabrieldchartier.compendia.ui.main.home.state.HomeViewState
import com.gabrieldchartier.compendia.util.AbsentLiveData
import javax.inject.Inject

class HomeViewModel
@Inject
constructor(val sessionManager: SessionManager, val homeRepository: HomeRepository): BaseViewModel<HomeStateEvent, HomeViewState>() {
    override fun initNewViewState(): HomeViewState {
        return HomeViewState()
    }

    override fun handleStateEvent(stateEvent: HomeStateEvent): LiveData<DataState<HomeViewState>> {
        when(stateEvent) {
            is ChangePasswordEvent -> {
                return sessionManager.cachedToken.value?.let { authToken ->
                    homeRepository.attemptChangePassword(authToken, stateEvent.currentPassword,
                            stateEvent.newPassword, stateEvent.newPasswordConfirmation)
                }?: AbsentLiveData.create()
            }

            is GetAccountPropertiesEvent -> {
                return sessionManager.cachedToken.value?.let { authToken ->
                    homeRepository.attemptGetAccountProperties(authToken)
                }?: AbsentLiveData.create()
            }

            is None -> {
                return object: LiveData<DataState<HomeViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        value = DataState.data(null, null)
                    }
                }
            }
        }
    }

    fun setAccountPropertiesData(accountProperties: AccountProperties) {
        val update = getCurrentViewStateOrNew()
        if(update.accountProperties == accountProperties)
            return
        update.accountProperties = accountProperties
        _viewState.value = update
    }

    fun setChangePasswordData(accountProperties: AccountProperties) {
        val update = getCurrentViewStateOrNew()
        if(update.accountProperties == accountProperties)
            return
        update.accountProperties = accountProperties
        _viewState.value = update
    }

    fun logout() {
        sessionManager.logout()
    }

    fun cancelActiveJobs() {
        setEmptyStateEvent()
        homeRepository.cancelActiveJobs()
    }

    fun setEmptyStateEvent() {
        setStateEvent(None())
    }
}