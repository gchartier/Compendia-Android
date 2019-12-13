package com.gabrieldchartier.compendia.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gabrieldchartier.compendia.R
import com.gabrieldchartier.compendia.ui.authentication.state.AuthStateEvent
import com.gabrieldchartier.compendia.ui.authentication.state.RegistrationFields
import com.gabrieldchartier.compendia.view_models.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_register.*
import javax.inject.Inject

class RegisterFragment : BaseAuthFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe the live data, and set on click listeners
        subscribeObservers()
        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setRegistrationFields(
                RegistrationFields(register_email.text.toString(),
                        register_username.text.toString(),register_password.text.toString(),
                        register_password_confirm.text.toString()))
    }

    private fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { authViewState ->
            authViewState.registrationFields?.let { regFields ->
                regFields.email?.let { register_email.setText(it) }
                regFields.username?.let { register_username.setText(it) }
                regFields.password?.let { register_password.setText(it) }
                regFields.password_confirmation?.let { register_password_confirm.setText(it) }
            }
        })
    }

    private fun setOnClickListeners() {
        sign_up_button.setOnClickListener {
            register()
        }
    }

    private fun register() {
        viewModel.setStateEvent(
                AuthStateEvent.RegisterAccountEvent(
                        register_email.text.toString(),
                        register_username.text.toString(),
                        register_password.text.toString(),
                        register_password_confirm.text.toString()
                )
        )
    }
}