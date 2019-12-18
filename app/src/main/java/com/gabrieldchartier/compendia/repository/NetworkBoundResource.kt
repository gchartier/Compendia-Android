package com.gabrieldchartier.compendia.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.gabrieldchartier.compendia.ui.DataState
import com.gabrieldchartier.compendia.ui.Response
import com.gabrieldchartier.compendia.ui.ResponseType
import com.gabrieldchartier.compendia.util.Constants.Companion.NETWORK_TIMEOUT
import com.gabrieldchartier.compendia.util.Constants.Companion.TESTING_CACHE_DELAY
import com.gabrieldchartier.compendia.util.Constants.Companion.TESTING_NETWORK_DELAY
import com.gabrieldchartier.compendia.util.ErrorHandling
import com.gabrieldchartier.compendia.util.ErrorHandling.Companion.ERROR_CHECK_NETWORK_CONNECTION
import com.gabrieldchartier.compendia.util.ErrorHandling.Companion.ERROR_UNKNOWN
import com.gabrieldchartier.compendia.util.ErrorHandling.Companion.UNABLE_TODO_OPERATION_WO_INTERNET
import com.gabrieldchartier.compendia.util.ErrorHandling.Companion.UNABLE_TO_RESOLVE_HOST
import com.gabrieldchartier.compendia.util.GenericAPIResponse
import com.gabrieldchartier.compendia.util.GenericAPIResponse.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

abstract class NetworkBoundResource<ResponseObject, ViewStateType, CacheObject>(
        isNetworkAvailable: Boolean, // is there a network connection?
        isNetworkRequest: Boolean,
        shouldLoadFromCache: Boolean
){
    protected val result = MediatorLiveData<DataState<ViewStateType>>()
    protected lateinit var job: CompletableJob
    protected lateinit var coroutineScope: CoroutineScope

    init {
        setJob(initNewJob())
        setValue(DataState.loading(isLoading = true, cachedData = null))

        if(shouldLoadFromCache) {
            val dbSource = loadFromCache()
            result.addSource(dbSource) { viewStateType ->
                result.removeSource(dbSource)
                setValue(DataState.loading(isLoading = true, cachedData = viewStateType))
            }
        }

        if(isNetworkRequest) {
            if(isNetworkAvailable) {
                coroutineScope.launch {
                    delay(TESTING_NETWORK_DELAY)
                    withContext(Main) {
                        val APIResponse = createCall()
                        result.addSource(APIResponse) { response ->
                            result.removeSource(APIResponse)
                            coroutineScope.launch {
                                handleNetworkCall(response)
                            }
                        }
                    }
                }
                GlobalScope.launch(IO) {
                    delay(NETWORK_TIMEOUT)

                    if(!job.isCompleted) {
                        Log.e("NetworkBoundResource", " (line 48): ")
                        job.cancel(CancellationException(UNABLE_TO_RESOLVE_HOST))
                    }
                }
            }
            else
                onErrorReturn(UNABLE_TODO_OPERATION_WO_INTERNET, shouldUseDialog = true, shouldUseToast = false)
        }
        else {
            coroutineScope.launch {
                // fake delay for testing cache
                delay(TESTING_CACHE_DELAY)
                createCacheRequestAndReturn()
            }
        }
    }

    suspend fun handleNetworkCall(response: GenericAPIResponse<ResponseObject>?) {
        when(response) {
            is APISuccessResponse -> {
                handleAPISuccessResponse(response)
            }

            is APIErrorResponse -> {
                Log.e("NetworkBoundResource", "handleNetworkCall (line 69): ${response.errorMessage}")
                onErrorReturn(response.errorMessage, shouldUseDialog = true, shouldUseToast = false)
            }

            is APIEmptyResponse -> {
                Log.e("NetworkBoundResource", "handleNetworkCall (line 69): Request returned nothing 204")
                onErrorReturn("HTTP 204. Returned nothing", shouldUseDialog = true, shouldUseToast = false)
            }
        }
    }

    fun onCompleteJob(dataState: DataState<ViewStateType>) {
        GlobalScope.launch(Main) {
            job.complete()
            setValue(dataState)
        }
    }

    private fun setValue(dataState: DataState<ViewStateType>) {
        result.value = dataState
    }

    fun onErrorReturn(errorMessage: String?, shouldUseDialog: Boolean, shouldUseToast: Boolean) {
        var message = errorMessage
        var useDialog = shouldUseDialog
        var responseType: ResponseType = ResponseType.None()
        if(message == null) {
            message = ERROR_UNKNOWN
        }
        else if(ErrorHandling.isNetworkError(message)) {
            message = ERROR_CHECK_NETWORK_CONNECTION
            useDialog = false
        }
        if(shouldUseToast) {
            responseType = ResponseType.Toast()
        }
        if(useDialog) {
            responseType = ResponseType.Dialog()
        }

        onCompleteJob(DataState.error(
                response = Response(
                        message = message,
                        responseType = responseType
                )
        ))
    }

    @UseExperimental(InternalCoroutinesApi::class)
    private fun initNewJob(): Job {
        Log.d("NetworkBoundResource", "initNewJob (line 18): ")
        job = Job()
        job.invokeOnCompletion(onCancelling = true, invokeImmediately = true, handler = object: CompletionHandler {
            override fun invoke(cause: Throwable?) {
                if(job.isCancelled) {
                    Log.e("NetworkBoundResource", "invoke (line 24): job was cancelled")
                    cause?.let {
                        onErrorReturn(it.message, shouldUseDialog = false, shouldUseToast = true)
                    }?: onErrorReturn(ERROR_UNKNOWN, shouldUseDialog = false, shouldUseToast = true)
                }
                else if(job.isCompleted) {
                    Log.e("NetworkBoundResource", "invoke (line 30): job is completed")
                    // Do nothing should be handled
                }
            }
        })
        coroutineScope = CoroutineScope(IO + job)
        return job
    }

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>

    abstract suspend fun createCacheRequestAndReturn()

    abstract suspend fun handleAPISuccessResponse(response: APISuccessResponse<ResponseObject>)

    abstract fun createCall(): LiveData<GenericAPIResponse<ResponseObject>>

    abstract fun loadFromCache(): LiveData<ViewStateType>

    abstract suspend fun updateLocalDB(cacheObject: CacheObject?)

    abstract fun setJob(job: Job)
}