package com.gabrieldchartier.compendia.ui.authentication.state

import com.gabrieldchartier.compendia.models.AuthenticationToken

data class AuthenticationViewState(var registrationFields: RegistrationFields? = RegistrationFields(),
                                   var loginFields: LoginFields? = LoginFields(),
                                   var authenticationToken: AuthenticationToken? = null)

data class RegistrationFields(
        var registration_email: String? = null,
        var registration_username: String? = null,
        var registration_password: String? = null,
        var registration_password_confirmation: String? = null
)  {
    class RegistrationError {
        companion object {
            fun mustFillAllFields(): String {
                return "All fields are required."
            }

            fun passwordDoNotMatch(): String {
                return "PAsswords must match."
            }

            fun none(): String {
                return "None"
            }
        }
    }

    fun isValidForRegistration(): String {
        if(registration_email.isNullOrEmpty() ||
                registration_username.isNullOrEmpty() ||
                registration_password.isNullOrEmpty() ||
                registration_password_confirmation.isNullOrEmpty()) {
            return RegistrationError.mustFillAllFields()
        }

        if (!registration_password.equals(registration_password_confirmation)) {
            return RegistrationError.passwordDoNotMatch()
        }

        return RegistrationError.none()

    }
}

data class LoginFields(
        var login_email: String? = null,
        var login_password: String? = null
) {
    class LoginError {

        companion object {

            fun mustFillAllFields(): String {
                return "You can't login without an email and password."
            }

            fun none(): String {
                return "None"
            }

        }
    }

    fun isValidForLogin(): String {

        if (login_email.isNullOrEmpty()
                || login_password.isNullOrEmpty()) {

            return LoginError.mustFillAllFields()
        }
        return LoginError.none()
    }
}