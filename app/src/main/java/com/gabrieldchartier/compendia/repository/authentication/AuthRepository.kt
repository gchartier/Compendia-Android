package com.gabrieldchartier.compendia.repository.authentication

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import com.gabrieldchartier.compendia.api.authentication.AuthService
import com.gabrieldchartier.compendia.api.authentication.network_responses.LoginResponse
import com.gabrieldchartier.compendia.api.authentication.network_responses.RegistrationResponse
import com.gabrieldchartier.compendia.models.AccountProperties
import com.gabrieldchartier.compendia.models.AuthToken
import com.gabrieldchartier.compendia.persistence.authentication.AccountPropertiesDAO
import com.gabrieldchartier.compendia.persistence.authentication.AuthTokenDAO
import com.gabrieldchartier.compendia.repository.JobManager
import com.gabrieldchartier.compendia.repository.NetworkBoundResource
import com.gabrieldchartier.compendia.session.SessionManager
import com.gabrieldchartier.compendia.ui.DataState
import com.gabrieldchartier.compendia.ui.Response
import com.gabrieldchartier.compendia.ui.ResponseType
import com.gabrieldchartier.compendia.ui.authentication.state.AuthViewState
import com.gabrieldchartier.compendia.ui.authentication.state.LoginFields
import com.gabrieldchartier.compendia.ui.authentication.state.RegistrationFields
import com.gabrieldchartier.compendia.util.AbsentLiveData
import com.gabrieldchartier.compendia.util.ErrorHandling.Companion.ERROR_SAVE_AUTH_TOKEN
import com.gabrieldchartier.compendia.util.ErrorHandling.Companion.GENERIC_AUTH_ERROR
import com.gabrieldchartier.compendia.util.GenericAPIResponse
import com.gabrieldchartier.compendia.util.GenericAPIResponse.*
import com.gabrieldchartier.compendia.util.PreferenceKeys
import com.gabrieldchartier.compendia.util.SuccessHandling.Companion.RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE
import kotlinx.coroutines.Job

class AuthRepository
constructor(
        val authTokenDAO: AuthTokenDAO,
        val accountPropertiesDAO: AccountPropertiesDAO,
        val authenticationService: AuthService,
        val sessionManager: SessionManager,
        val sharedPreferences: SharedPreferences,
        val sharedPrefsEditor: SharedPreferences.Editor
): JobManager("AuthRepository") {

    fun attemptLogin(email: String, password: String): LiveData<DataState<AuthViewState>> {
        val loginFieldErrors = LoginFields(email, password).validateLogin()
        if(loginFieldErrors != LoginFields.LoginError.none()) {
            return returnErrorResponse(loginFieldErrors, ResponseType.Dialog())
        }

        return object: NetworkBoundResource<LoginResponse, AuthViewState, Any>(
                sessionManager.isConnectedToInternet(),
                isNetworkRequest = true,
                shouldLoadFromCache = false,
                shouldCancelIfNoInternet = true
        )
        {

            override suspend fun handleAPISuccessResponse(response: APISuccessResponse<LoginResponse>) {
                Log.d("AuthenticationRepositor", "handleAPISuccessResponse (line 43): ${response.body.email}")
                // Incorrect login creds counts as a 200 response from server, so need to handle that
                if(response.body.response.equals(GENERIC_AUTH_ERROR)) {
                    return onErrorReturn(response.body.errorMessage, true, false)
                }

                val newResult = accountPropertiesDAO.insertAndReplace(
                        AccountProperties(
                                response.body.pk,
                                response.body.email,
                                ""
                        )
                )

                Log.e("AuthRepository", "handleAPISuccessResponse (line 69): accountinsert result = $newResult")
                Log.e("AuthRepository", "handleAPISuccessResponse (line 70): ${accountPropertiesDAO.searchByPK(25).value}")

                val result = authTokenDAO.insert(
                        AuthToken(
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
                    AuthViewState(authToken =
                        AuthToken(response.body.pk, response.body.token))))
            }

            override fun createCall(): LiveData<GenericAPIResponse<LoginResponse>> {
                return authenticationService.login(email, password)
            }

            override fun setJob(job: Job) {
                addJob("attemptLogin", job)
            }

            // Cache functionality is not used for this request
            override suspend fun createCacheRequestAndReturn() { }
            override fun loadFromCache(): LiveData<AuthViewState> {return AbsentLiveData.create()}
            override suspend fun updateLocalDB(cacheObject: Any?) {}

        }.asLiveData()
    }

    fun attemptRegistration(
            email: String,
            username: String,
            password: String,
            passwordConfirmation: String): LiveData<DataState<AuthViewState>> {
        val registrationFieldErrors = RegistrationFields(
                email, username, password, passwordConfirmation).validateRegistration()
        if(!registrationFieldErrors.equals(RegistrationFields.RegistrationError.none())) {
            return returnErrorResponse(registrationFieldErrors, ResponseType.Dialog())
        }

        return object: NetworkBoundResource<RegistrationResponse, AuthViewState, Any> (
                sessionManager.isConnectedToInternet(),
                isNetworkRequest = true,
                shouldLoadFromCache = false,
                shouldCancelIfNoInternet = true
        )
        {
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
                        AuthToken(
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
                    AuthViewState(authToken =
                        AuthToken(response.body.pk, response.body.token))))
            }

            override fun createCall(): LiveData<GenericAPIResponse<RegistrationResponse>> {
                return authenticationService.register(email, username, password, passwordConfirmation)
            }

            override fun setJob(job: Job) {
                addJob("attemptRegistration", job)
            }

            // Cache functionality is not used for this request
            override fun loadFromCache(): LiveData<AuthViewState> { return AbsentLiveData.create() }
            override suspend fun updateLocalDB(cacheObject: Any?) { }
            override suspend fun createCacheRequestAndReturn() { }
        }.asLiveData()
    }

    private fun returnErrorResponse(errorMessage: String, responseType: ResponseType): LiveData<DataState<AuthViewState>> {
        Log.d("AuthenticationRepositor", "returnErrorResponse (line 107): $errorMessage")
        return object: LiveData<DataState<AuthViewState>>() {
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

    private fun saveAuthenticatedUserToSharedPrefs(email: String) {
        sharedPrefsEditor.putString(PreferenceKeys.PREVIOUS_AUTH_USER, email)
        sharedPrefsEditor.apply()
    }

    fun checkPreviouslyAuthenticatedUser(): LiveData<DataState<AuthViewState>> {
        val previousAuthUserEmail: String? =
                sharedPreferences.getString(PreferenceKeys.PREVIOUS_AUTH_USER, null)

        if(previousAuthUserEmail.isNullOrBlank()) {
            Log.d("AuthenticationRepositor", "checkPreviouslyAuthenticatedUser (line 194): No previously authenticated user found...")
            return returnNoTokenFound()
        }
        else {
            Log.d("AuthRepository", "checkPreviouslyAuthenticatedUser (line 201): Previously auth user found")
            return object: NetworkBoundResource<Void, AuthViewState, Any> (
                    sessionManager.isConnectedToInternet(),
                    isNetworkRequest = false,
                    shouldLoadFromCache = false,
                    shouldCancelIfNoInternet = false
            )
            {
                override suspend fun createCacheRequestAndReturn() {
                    Log.e("AuthRepository", "createCacheRequestAndReturn (line 210): checking for auth user with email $previousAuthUserEmail")
                    accountPropertiesDAO.searchByEmail(previousAuthUserEmail).let {  accountProperties ->
                        Log.d("AuthenticationRepositor", "createCacheRequestAndReturn (line 205): searching for token $accountProperties")
                            accountProperties.let {
                                Log.e("AuthRepository", "createCacheRequestAndReturn (line 213): accountproperties value was not null")
                                if(it != null)
                                {
                                    Log.e("AuthRepository", "createCacheRequestAndReturn (line 213): it was not null")
                                    authTokenDAO.searchByPk(it.pk).let { authToken ->
                                        Log.e("AuthRepository", "createCacheRequestAndReturn (line 218): authtoken retrieved from search was not null")
                                        if(authToken != null) {
                                            Log.e("AuthRepository", "createCacheRequestAndReturn (line 220): authtoken was not null")
                                            onCompleteJob(DataState.data(data= AuthViewState(authToken = authToken)))
                                            return
                                        }
                                    }
                                }
                            }
                        Log.d("AuthenticationRepositor", "createCacheRequestAndReturn (line 216): Authtoken not found...   ${accountPropertiesDAO.searchByEmail(previousAuthUserEmail)?.email}")
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
                    addJob("checkPreviouslyAuthenticatedUser", job)
                }

                // Cache functionality is not used in this request
                override fun loadFromCache(): LiveData<AuthViewState> { return AbsentLiveData.create() }
                override suspend fun updateLocalDB(cacheObject: Any?) { }

            }.asLiveData()
        }

    }

    private fun returnNoTokenFound(): LiveData<DataState<AuthViewState>> {
        return object: LiveData<DataState<AuthViewState>>() {
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