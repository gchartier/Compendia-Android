package com.gabrieldchartier.compendia

import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.gabrieldchartier.compendia.session.SessionManager
import com.gabrieldchartier.compendia.ui.*
import com.gabrieldchartier.compendia.util.displayErrorDialog
import com.gabrieldchartier.compendia.util.displaySuccessDialog
import com.gabrieldchartier.compendia.util.displayToast
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity(), DataStateChangeListener {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun hideKeyboard() {
        if(currentFocus != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
        else
            Log.e("HideKeyboard", "currentFocus was null")
    }

    // Updates progress bar, and displays the response or error of the data state
    override fun onDataStateChange(dataState: DataState<*>?) {
        dataState?.let {
            GlobalScope.launch(Main) {
                displayProgressBar(it.loading.isLoading)

                it.data?.let {
                    it.response?.let { eventResponse ->
                        handleStateEventResponse(eventResponse)
                    }
                }

                it.error?.let { errorEvent ->
                    handleStateEventError(errorEvent)
                }
            }
        }
    }

    // Defined in the inheriting activity to display the progress bar in the given activity
    abstract fun displayProgressBar(bool: Boolean)

    // Displays the event response in a toast, dialog, or nothing depending on the response type of the event
    private fun handleStateEventResponse(event: Event<Response>) {
        Log.d("BaseActivity", "handleStateEventResponse (line 44)")
        event.getContentIfNotHandled()?.let {
            when(it.responseType) {
                is ResponseType.Toast -> {
                    it.message?.let { message ->
                        displayToast(message)
                    }
                }

                is ResponseType.Dialog -> {
                    it.message?.let { message ->
                        displaySuccessDialog(message)
                    }
                }

                is ResponseType.None -> {
                    Log.e("BaseActivity", "handleStateEventError (line 58): ${it.message}")
                }
            }
        }
    }

    // Displays the event error in a toast, dialog, or nothing depending on the response type of the event
    private fun handleStateEventError(event: Event<StateError>) {
        event.getContentIfNotHandled()?.let {
            when(it.response.responseType) {
                is ResponseType.Toast -> {
                    it.response.message?.let { message ->
                        displayToast(message)
                    }
                }

                is ResponseType.Dialog -> {
                    it.response.message?.let { errorMessage ->
                        displayErrorDialog(errorMessage)
                    }
                }

                is ResponseType.None -> {
                    Log.e("BaseActivity", "handleStateEventError (line 57): ${it.response.message}")
                }
            }
        }
    }
}