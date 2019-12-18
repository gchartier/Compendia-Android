package com.gabrieldchartier.compendia.ui.authentication

import androidx.lifecycle.LiveData
import com.gabrieldchartier.compendia.models.AuthToken
import com.gabrieldchartier.compendia.repository.authentication.AuthRepository
import com.gabrieldchartier.compendia.ui.BaseViewModel
import com.gabrieldchartier.compendia.ui.DataState
import com.gabrieldchartier.compendia.ui.authentication.state.*
import com.gabrieldchartier.compendia.ui.authentication.state.AuthStateEvent.*
import com.gabrieldchartier.compendia.util.AbsentLiveData
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
        authRepository.cancelActiveJobs()
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}