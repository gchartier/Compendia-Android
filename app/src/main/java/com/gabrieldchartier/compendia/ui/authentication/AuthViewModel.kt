package com.gabrieldchartier.compendia.ui.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gabrieldchartier.compendia.models.AccountProperties
import com.gabrieldchartier.compendia.models.AuthToken
import com.gabrieldchartier.compendia.repository.authentication.AuthRepository
import com.gabrieldchartier.compendia.ui.BaseViewModel
import com.gabrieldchartier.compendia.ui.DataState
import com.gabrieldchartier.compendia.ui.authentication.state.*
import com.gabrieldchartier.compendia.ui.authentication.state.AuthStateEvent.*
import com.gabrieldchartier.compendia.util.AbsentLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthViewModel
@Inject
constructor(private val authRepository: AuthRepository):
        BaseViewModel<AuthStateEvent, AuthViewState>()
{
    override fun initNewViewState(): AuthViewState {
        return AuthViewState()
    }

    override fun handleStateEvent(stateEvent: AuthStateEvent): LiveData<DataState<AuthViewState>> {

        when(stateEvent) {

            is LoginEvent -> return authRepository.attemptLogin(stateEvent.email, stateEvent.password)

            is RegisterAccountEvent -> return authRepository.attemptRegistration( stateEvent.email,
                        stateEvent.username, stateEvent.password, stateEvent.password_confirmation )

            is AutoAuthenticateEvent -> return authRepository.checkPreviouslyAuthenticatedUser()

            is None -> return object: LiveData<DataState<AuthViewState>>() {
                override fun onActive() {
                    super.onActive()
                    value = DataState.data(null, null)
                }
            }
        }

        //todo handle this properly
        return AbsentLiveData.create()
    }

    fun setRegistrationFields(registrationFields: RegistrationFields) {
        val update = getCurrentViewStateOrNew()
        if(update.registrationFields == registrationFields)
            return
        else {
            update.registrationFields = registrationFields
            _viewState.value = update
        }
    }

    fun setLoginFields(loginFields: LoginFields) {
        val update = getCurrentViewStateOrNew()
        if(update.loginFields == loginFields)
            return
        else {
            update.loginFields = loginFields
            _viewState.value = update
        }
    }

    fun setAuthenticationToken(authenticationToken: AuthToken) {
        val update = getCurrentViewStateOrNew()
        if(update.authToken == authenticationToken)
            return
        else {
            update.authToken = authenticationToken
            _viewState.value = update
        }
    }

    fun cancelActiveJobs() {
        setEmptyStateEvent()
        authRepository.cancelActiveJobs()
    }

    fun setEmptyStateEvent() {
        setStateEvent(None())
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}