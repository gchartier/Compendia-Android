package com.gabrieldchartier.compendia.ui.authentication.state

sealed class AuthenticationStateEvent {

    data class LoginAttemptEvent(
            val email: String,
            val password: String
    ): AuthenticationStateEvent()

    data class RegisterAttemptEvent(
            val email: String,
            val username: String,
            val password: String,
            val password_confirmation: String
    ): AuthenticationStateEvent()

    class ReauthenticateEvent: AuthenticationStateEvent()
}