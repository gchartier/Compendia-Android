package com.gabrieldchartier.compendia.ui.authentication.state

import com.gabrieldchartier.compendia.models.AuthToken

data class AuthViewState(var registrationFields: RegistrationFields? = RegistrationFields(),
                         var loginFields: LoginFields? = LoginFields(),
                         var authToken: AuthToken? = null)

data class RegistrationFields(
        var email: String? = null,
        var username: String? = null,
        var password: String? = null,
        var password_confirmation: String? = null
) {
    class RegistrationError {
        companion object {
            fun mustFillAllFields(): String {
                return "All fields are required."
            }

            fun passwordsDoNotMatch(): String {
                return "Passwords must match."
            }

            fun none(): String {
                return "None"
            }
        }
    }

    fun validateRegistration(): String {
        if(email.isNullOrEmpty() || username.isNullOrEmpty() ||
                password.isNullOrEmpty() || password_confirmation.isNullOrEmpty())
            return RegistrationError.mustFillAllFields()

        if (!password.equals(password_confirmation)) {
            return RegistrationError.passwordsDoNotMatch()
        }

        return RegistrationError.none()

    }
}

data class LoginFields(
        var email: String? = null,
        var password: String? = null
) {
    class LoginError {

        companion object {

            fun mustFillAllFields(): String {
                return "Please enter an email and a password."
            }

            fun none(): String {
                return "None"
            }
        }
    }

    fun validateLogin(): String {

        if (email.isNullOrEmpty() || password.isNullOrEmpty())
            return LoginError.mustFillAllFields()

        return LoginError.none()
    }
}