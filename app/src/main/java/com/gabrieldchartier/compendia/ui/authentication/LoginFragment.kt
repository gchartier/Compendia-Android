package com.gabrieldchartier.compendia.ui.authentication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gabrieldchartier.compendia.R
import com.gabrieldchartier.compendia.models.AccountProperties
import com.gabrieldchartier.compendia.ui.authentication.state.AuthStateEvent
import com.gabrieldchartier.compendia.ui.authentication.state.LoginFields
import com.gabrieldchartier.compendia.view_models.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginFragment : BaseAuthFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe the live data, and set on click listeners
        subscribeObservers()
        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setLoginFields(LoginFields(register_email.text.toString(),
                loginPassword.text.toString()))
    }

    fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { authViewState ->
            authViewState.loginFields?.let { loginFields ->
                loginFields.email?.let { register_email.setText(it) }
                loginFields.password?.let { loginPassword.setText(it) }
            }
        })


    }

    private fun setOnClickListeners() {
        loginButton.setOnClickListener {
            login()
        }
        signUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }

    private fun login() {
        viewModel.setStateEvent(
                AuthStateEvent.LoginEvent(
                        register_email.text.toString(),
                        loginPassword.text.toString()
                )
        )
    }
}
