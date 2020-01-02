package com.gabrieldchartier.compendia.ui.authentication.state

sealed class AuthStateEvent {

    data class LoginEvent(
            val email: String,
            val password: String
    ): AuthStateEvent()

    data class RegisterAccountEvent(
            val email: String,
            val username: String,
            val password: String,
            val password_confirmation: String
    ): AuthStateEvent()

    data class ResetPasswordEvent(
            val email: String
    ): AuthStateEvent()

    class AutoAuthenticateEvent: AuthStateEvent()

    class None: AuthStateEvent()
}