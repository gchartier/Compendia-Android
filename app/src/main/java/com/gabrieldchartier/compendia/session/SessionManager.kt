package com.gabrieldchartier.compendia.session

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gabrieldchartier.compendia.models.AuthToken
import com.gabrieldchartier.compendia.persistence.authentication.AuthTokenDAO
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager
@Inject
constructor(private val authTokenDAO: AuthTokenDAO, val application: Application)
{
    private val _cachedToken = MutableLiveData<AuthToken>()

    val cachedToken: LiveData<AuthToken>
        get() = _cachedToken

    fun login(token: AuthToken) {
        setCachedToken(token)
    }

    fun logout() {
        Log.d("SessionManager", "logout (line 26): logout")

        GlobalScope.launch(IO) {
            var errorMessage: String? = null
            try {
                cachedToken.value!!.account_pk?.let {
                    authTokenDAO.nullifyToken(it)
                }
            }
            catch (e: CancellationException) {
                Log.e("SessionManager", "logout (line 37): ${e.message}")
                errorMessage = e.message
            }
            catch (e: Exception) {
                Log.e("SessionManager", "logout (line 41): ${e.message}")
                errorMessage = e.message
            }
            finally {
                errorMessage?.let {
                    Log.e("SessionManager", "logout (line 46): $errorMessage")
                }
                Log.d("SessionManager", "logout (line 48): finally")
                setCachedToken(null)
            }
        }
    }

    private fun setCachedToken(token: AuthToken?) {
        GlobalScope.launch(Main) {
            if(_cachedToken.value != token)
                _cachedToken.value = token
        }
    }

    fun isConnectedToInternet(): Boolean {
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        try {
            return connectivityManager.activeNetworkInfo.isConnected
        }
        catch (e: Exception) {
            Log.e("SessionManager", "isConnectedToInternet (line 76): ${e.message}")
        }
        return false
    }
}