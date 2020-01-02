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
import com.gabrieldchartier.compendia.ui.authentication.state.AuthStateEvent
import com.gabrieldchartier.compendia.ui.main.MainActivity
import com.gabrieldchartier.compendia.view_models.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_authentication.*
import javax.inject.Inject

class AuthActivity : BaseActivity(), NavController.OnDestinationChangedListener {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        // Inject the view model, get the nav controller, subscribe the live data observers, and handle auto-authentication
        viewModel = ViewModelProvider(this, providerFactory).get(AuthViewModel::class.java)
        findNavController(R.id.authentication_nav_host_fragment).addOnDestinationChangedListener(this)
        subscribeObservers()
    }

    override fun onResume() {
        super.onResume()
        triggerAutoAuthenticateEvent()
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        viewModel.cancelActiveJobs()
    }

    private fun subscribeObservers() {

        viewModel.dataState.observe(this, Observer { dataState ->
            onDataStateChange(dataState)
            dataState.data?.let { data ->
                data.data?.let { event ->
                    event.getContentIfNotHandled()?.let {
                        it.authToken?.let { token ->
                            Log.d("AuthenticationActivity", "subscribeObservers (line 50): $token")
                            viewModel.setAuthenticationToken(token)
                        }
                    }
                }
            }
        })

        viewModel.viewState.observe(this, Observer {
            it.authToken?.let { token ->
                sessionManager.login(token)
            }
        })

        sessionManager.cachedToken.observe(this, Observer { dataState ->
            Log.d("AuthActivity", "subscribeObservers (line 65): AuthToken $dataState")
            dataState.let { authToken ->
                if(authToken != null && authToken.account_pk != -1 && authToken.token != null) {
                    navMainActivity()
                    finish()
                }
            }
        })
    }

    private fun navMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Overridden from BaseActivity. Toggles the progress bar when necessary
    override fun displayProgressBar(bool: Boolean) {
        if(bool)
            progress_bar.visibility = View.VISIBLE
        else
            progress_bar.visibility = View.GONE
    }

    private fun triggerAutoAuthenticateEvent() {
        viewModel.setStateEvent(AuthStateEvent.AutoAuthenticateEvent())
    }
}
