package com.gabrieldchartier.compendia.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gabrieldchartier.compendia.api.authentication.network_responses.LoginResponse
import com.gabrieldchartier.compendia.api.authentication.network_responses.RegistrationResponse
import com.gabrieldchartier.compendia.models.AuthenticationToken
import com.gabrieldchartier.compendia.repository.authentication.AuthenticationRepository
import com.gabrieldchartier.compendia.ui.BaseViewModel
import com.gabrieldchartier.compendia.ui.DataState
import com.gabrieldchartier.compendia.ui.authentication.state.AuthenticationStateEvent
import com.gabrieldchartier.compendia.ui.authentication.state.AuthenticationStateEvent.*
import com.gabrieldchartier.compendia.ui.authentication.state.AuthenticationViewState
import com.gabrieldchartier.compendia.ui.authentication.state.LoginFields
import com.gabrieldchartier.compendia.ui.authentication.state.RegistrationFields
import com.gabrieldchartier.compendia.util.AbsentLiveData
import com.gabrieldchartier.compendia.util.GenericAPIResponse
import javax.inject.Inject

class AuthenticationViewModel
@Inject
constructor(val authenticationRepository: AuthenticationRepository):
        BaseViewModel<AuthenticationStateEvent, AuthenticationViewState>()
{
    override fun initNewViewState(): AuthenticationViewState {
        return AuthenticationViewState()
    }

    override fun handleStateEvent(stateEvent: AuthenticationStateEvent): LiveData<DataState<AuthenticationViewState>> {
        when(stateEvent) {
            is LoginAttemptEvent -> {
                return authenticationRepository.attemptLogin(stateEvent.email, stateEvent.password)
            }

            is RegisterAttemptEvent -> {
                return authenticationRepository.attemptRegistration(
                        stateEvent.email,
                        stateEvent.username,
                        stateEvent.password,
                        stateEvent.password_confirmation
                )
            }

            is ReauthenticateEvent -> {
                return authenticationRepository.checkPreviouslyAuthenticatedUser()
            }
        }
    }

    fun setRegistrationFields(registrationFields: RegistrationFields) {
        val update = getCurrentViewStateOrNew()
        if(update.registrationFields == registrationFields) {
            return
        }
        update.registrationFields = registrationFields
        _viewState.value = update
    }

    fun setLoginFields(loginFields: LoginFields) {
        val update = getCurrentViewStateOrNew()
        if(update.loginFields == loginFields) {
            return
        }
        update.loginFields = loginFields
        _viewState.value = update
    }

    fun setAuthenticationToken(authenticationToken: AuthenticationToken) {
        val update = getCurrentViewStateOrNew()
        if(update.authenticationToken == authenticationToken) {
            return
        }
        update.authenticationToken = authenticationToken
        _viewState.value = update
    }

    fun cancelActiveJobs() {
        authenticationRepository.cancelActiveJobs()
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}