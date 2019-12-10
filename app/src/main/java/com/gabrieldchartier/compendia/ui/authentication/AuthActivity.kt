package com.gabrieldchartier.compendia.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.gabrieldchartier.compendia.BaseActivity
import com.gabrieldchartier.compendia.R
import com.gabrieldchartier.compendia.ui.authentication.state.AuthenticationStateEvent
import com.gabrieldchartier.compendia.ui.main.MainActivity
import com.gabrieldchartier.compendia.view_models.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_authentication.*
import javax.inject.Inject

class AuthenticationActivity : BaseActivity(), NavController.OnDestinationChangedListener {
    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: AuthenticationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        viewModel = ViewModelProvider(this, providerFactory).get(AuthenticationViewModel::class.java)
        findNavController(R.id.authentication_nav_host_fragment).addOnDestinationChangedListener(this)

        subscribeObservers()
        checkPreviouslyAuthenticatedUser()
    }

    private fun subscribeObservers() {

        viewModel.dataState.observe(this, Observer { dataState ->
            onDataStateChange(dataState)
            dataState.data?.let { data ->
                data.data?.let { event ->
                    event.getContentIfNotHandled()?.let {
                        it.authenticationToken?.let { token ->
                            Log.d("AuthenticationActivity", "subscribeObservers (line 36): $token")
                            viewModel.setAuthenticationToken(token)
                        }
                    }
                }
            }
        })

        viewModel.viewState.observe(this, Observer {
            it.authenticationToken?.let { token ->
                sessionManager.login(token)
            }
        })

        sessionManager.cachedToken.observe(this, Observer {
            Log.d("AuthenticationActivity", "subscribeObservers (line 28): AuthToken $it")
            if(it != null && it.account_pk != -1 && it.token != null) {
                navMainActivity()
                finish()
            }
        })
    }

    private fun navMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        viewModel.cancelActiveJobs()
    }

    override fun displayProgressBar(bool: Boolean) {
        if(bool)
            progress_bar.visibility = View.VISIBLE
        else
            progress_bar.visibility = View.GONE
    }

    fun checkPreviouslyAuthenticatedUser() {
        viewModel.setStateEvent(AuthenticationStateEvent.ReauthenticateEvent())
    }
}
