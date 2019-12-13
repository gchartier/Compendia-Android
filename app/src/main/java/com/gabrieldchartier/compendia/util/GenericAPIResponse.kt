package com.gabrieldchartier.compendia.util

import android.util.Log
import retrofit2.Response

/**
 * Copied from Architecture components google sample:
 * https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/api/ApiResponse.kt
 */
@Suppress("unused") // T is used in extending classes
sealed class GenericAPIResponse<T> {

    /**
     * separate class for HTTP 204 responses so that we can make APISuccessResponse's body non-null.
     */
    class APIEmptyResponse<T> : GenericAPIResponse<T>()

    data class APISuccessResponse<T>(val body: T) : GenericAPIResponse<T>() {}

    data class APIErrorResponse<T>(val errorMessage: String) : GenericAPIResponse<T>()

    companion object {
        private val TAG: String = "AppDebug"


        fun <T> create(error: Throwable): APIErrorResponse<T> {
            return APIErrorResponse(
                    error.message ?: "unknown error"
            )
        }

        fun <T> create(response: Response<T>): GenericAPIResponse<T> {

            Log.d(TAG, "GenericAPIResponse: response: ${response}")
            Log.d(TAG, "GenericAPIResponse: raw: ${response.raw()}")
            Log.d(TAG, "GenericAPIResponse: headers: ${response.headers()}")
            Log.d(TAG, "GenericAPIResponse: message: ${response.message()}")

            if(response.isSuccessful){
                val body = response.body()
                return if (body == null || response.code() == 204) {
                    APIEmptyResponse()
                } else if(response.code() == 401){
                    APIErrorResponse("401 Unauthorized. Token may be invalid.")
                } else {
                    APISuccessResponse(body = body)
                }
            }
            else{
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                return APIErrorResponse(
                        errorMsg ?: "unknown error"
                )
            }
        }
    }
}