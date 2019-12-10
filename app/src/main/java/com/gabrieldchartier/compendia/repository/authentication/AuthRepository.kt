package com.gabrieldchartier.compendia.repository.authentication

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.gabrieldchartier.compendia.api.authentication.AuthenticationService
import com.gabrieldchartier.compendia.api.authentication.network_responses.LoginResponse
import com.gabrieldchartier.compendia.api.authentication.network_responses.RegistrationResponse
import com.gabrieldchartier.compendia.models.AccountProperties
import com.gabrieldchartier.compendia.models.AuthenticationToken
import com.gabrieldchartier.compendia.persistence.authentication.AccountPropertiesDAO
import com.gabrieldchartier.compendia.persistence.authentication.AuthenticationTokenDAO
import com.gabrieldchartier.compendia.repository.NetworkBoundResource
import com.gabrieldchartier.compendia.session.SessionManager
import com.gabrieldchartier.compendia.ui.Data
import com.gabrieldchartier.compendia.ui.DataState
import com.gabrieldchartier.compendia.ui.Response
import com.gabrieldchartier.compendia.ui.ResponseType
import com.gabrieldchartier.compendia.ui.authentication.state.AuthenticationViewState
import com.gabrieldchartier.compendia.ui.authentication.state.LoginFields
import com.gabrieldchartier.compendia.ui.authentication.state.RegistrationFields
import com.gabrieldchartier.compendia.util.AbsentLiveData
import com.gabrieldchartier.compendia.util.ErrorHandling.Companion.ERROR_SAVE_AUTH_TOKEN
import com.gabrieldchartier.compendia.util.ErrorHandling.Companion.ERROR_UNKNOWN
import com.gabrieldchartier.compendia.util.ErrorHandling.Companion.GENERIC_AUTH_ERROR
import com.gabrieldchartier.compendia.util.GenericAPIResponse
import com.gabrieldchartier.compendia.util.GenericAPIResponse.*
import com.gabrieldchartier.compendia.util.PreferenceKeys
import com.gabrieldchartier.compendia.util.SuccessHandling.Companion.RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE
import kotlinx.coroutines.Job

class AuthenticationRepository
constructor(
        val authTokenDAO: AuthenticationTokenDAO,
        val accountPropertiesDAO: AccountPropertiesDAO,
        val authenticationService: AuthenticationService,
        val sessionManager: SessionManager,
        val sharedPreferences: SharedPreferences,
        val sharedPrefsEditor: SharedPreferences.Editor
)
{
    private var repositoryJob: Job? = null

    fun attemptLogin(email: String, password: String): LiveData<DataState<AuthenticationViewState>> {
        val loginFieldErrors = LoginFields(email, password).isValidForLogin()
        if(!loginFieldErrors.equals(LoginFields.LoginError.none())) {
            return returnErrorResponse(loginFieldErrors, ResponseType.Dialog())
        }

        return object: NetworkBoundResource<LoginResponse, AuthenticationViewState>(
                sessionManager.isConnectedToInternet(), true
        ){
            override suspend fun handleAPISuccessResponse(response: APISuccessResponse<LoginResponse>) {
                Log.d("AuthenticationRepositor", "handleAPISuccessResponse (line 43): $response")
                // Incorrect login creds counts as a 200 response from server, so need to handle that
                if(response.body.response.equals(GENERIC_AUTH_ERROR)) {
                    return onErrorReturn(response.body.errorMessage, true, false)
                }

                accountPropertiesDAO.insertOrIgnore(
                        AccountProperties(
                                response.body.pk,
                                response.body.email,
                                ""
                        )
                )

                val result = authTokenDAO.insert(
                        AuthenticationToken(
                                response.body.pk,
                                response.body.token
                        )
                )

                if(result < 0) {
                    return onCompleteJob(
                            DataState.error(Response(ERROR_SAVE_AUTH_TOKEN, ResponseType.Dialog()))
                    )
                }

                saveAuthenticatedUserToSharedPrefs(email)

                onCompleteJob(DataState.data(data =
                    AuthenticationViewState(authenticationToken =
                        AuthenticationToken(response.body.pk, response.body.token))))
            }

            override fun createCall(): LiveData<GenericAPIResponse<LoginResponse>> {
                return authenticationService.login(email, password)
            }

            override fun setJob(job: Job) {
                repositoryJob?.cancel()
                repositoryJob = job
            }

            // Not used in this case
            override suspend fun createCacheRequestAndReturn() { }

        }.asLiveData()
    }

    fun attemptRegistration(
            email: String,
            username: String,
            password: String,
            passwordConfirmation: String): LiveData<DataState<AuthenticationViewState>> {
        val registrationFieldErrors = RegistrationFields(
                email, username, password, passwordConfirmation).isValidForRegistration()
        if(!registrationFieldErrors.equals(RegistrationFields.RegistrationError.none())) {
            return returnErrorResponse(registrationFieldErrors, ResponseType.Dialog())
        }

        return object: NetworkBoundResource<RegistrationResponse, AuthenticationViewState> (
                sessionManager.isConnectedToInternet(), true
        ){
            override suspend fun handleAPISuccessResponse(response: APISuccessResponse<RegistrationResponse>) {
                Log.d("AuthenticationRepositor", "handleAPISuccessResponse (line 85): $response")
                if(response.body.response.equals(GENERIC_AUTH_ERROR))
                    return onErrorReturn(response.body.errorMessage, true, false)

                accountPropertiesDAO.insertOrIgnore(
                        AccountProperties(
                                response.body.pk,
                                response.body.email,
                                ""
                        )
                )

                val result = authTokenDAO.insert(
                        AuthenticationToken(
                                response.body.pk,
                                response.body.token
                        )
                )

                if(result < 0) {
                    return onCompleteJob(
                            DataState.error(Response(ERROR_SAVE_AUTH_TOKEN, ResponseType.Dialog()))
                    )
                }

                saveAuthenticatedUserToSharedPrefs(email)

                onCompleteJob(DataState.data(data =
                    AuthenticationViewState(authenticationToken =
                        AuthenticationToken(response.body.pk, response.body.token))))
            }

            override fun createCall(): LiveData<GenericAPIResponse<RegistrationResponse>> {
                return authenticationService.register(email, username, password, passwordConfirmation)
            }

            override fun setJob(job: Job) {
                repositoryJob?.cancel()
                repositoryJob = job
            }

            // Not used in this case
            override suspend fun createCacheRequestAndReturn() { }

        }.asLiveData()
    }

    private fun returnErrorResponse(errorMessage: String, responseType: ResponseType): LiveData<DataState<AuthenticationViewState>> {
        Log.d("AuthenticationRepositor", "returnErrorResponse (line 107): $errorMessage")
        return object: LiveData<DataState<AuthenticationViewState>>() {
            override fun onActive() {
                super.onActive()
                value = DataState.error(
                        Response(
                                errorMessage,
                                responseType
                        )
                )
            }
        }
    }

    fun cancelActiveJobs() {
        Log.d("AuthenticationRepository", "cancelActiveJobs (line 84): cancelling jobs")
        repositoryJob?.cancel()
    }

    private fun saveAuthenticatedUserToSharedPrefs(email: String) {
        sharedPrefsEditor.putString(PreferenceKeys.PREVIOUS_AUTH_USER, email)
        sharedPrefsEditor.apply()
    }

    fun checkPreviouslyAuthenticatedUser(): LiveData<DataState<AuthenticationViewState>> {
        val previousAuthUserEmail: String? =
                sharedPreferences.getString(PreferenceKeys.PREVIOUS_AUTH_USER, null)

        if(previousAuthUserEmail.isNullOrBlank()) {
            Log.d("AuthenticationRepositor", "checkPreviouslyAuthenticatedUser (line 194): No previously authenticated user found...")
            return returnNoTokenFound()
        }
        else {
            return object: NetworkBoundResource<Void, AuthenticationViewState> (
                    sessionManager.isConnectedToInternet(), false
            ){
                override suspend fun createCacheRequestAndReturn() {
                    accountPropertiesDAO.searchByEmail(previousAuthUserEmail).let {  accountProperties ->
                        Log.d("AuthenticationRepositor", "createCacheRequestAndReturn (line 205): searching for token $accountProperties")
                        accountProperties?.let {
                            if(accountProperties.pk > -1) {
                                authTokenDAO.searchByPk(accountProperties.pk).let { authToken ->
                                    if(authToken != null) {
                                        onCompleteJob(DataState.data(data= AuthenticationViewState(authenticationToken = authToken)))
                                        return
                                    }
                                }
                            }
                        }
                        Log.d("AuthenticationRepositor", "createCacheRequestAndReturn (line 216): Authtoken not found...")
                        onCompleteJob(DataState.data(data=null, response =
                            Response(RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE, ResponseType.None())))
                    }
                }

                // Not used in this case
                override suspend fun handleAPISuccessResponse(response: APISuccessResponse<Void>) { }

                // Not used in this case
                override fun createCall(): LiveData<GenericAPIResponse<Void>> {
                    return AbsentLiveData.create()
                }

                override fun setJob(job: Job) {
                    repositoryJob?.cancel()
                    repositoryJob = job
                }

            }.asLiveData()
        }

    }

    private fun returnNoTokenFound(): LiveData<DataState<AuthenticationViewState>> {
        return object: LiveData<DataState<AuthenticationViewState>>() {
            override fun onActive() {
                super.onActive()
                value = DataState.data(
                        data = null,
                        response = Response(RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE, ResponseType.None())
                )
            }
        }
    }
}