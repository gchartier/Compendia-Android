package com.gabrieldchartier.compendia.ui.main.home.state

import com.gabrieldchartier.compendia.models.AccountProperties
import com.gabrieldchartier.compendia.models.Comic
import com.gabrieldchartier.compendia.models.NewRelease

class HomeViewState(
        var accountProperties: AccountProperties? = null,
        var changePasswordFields: ChangePasswordFields? = ChangePasswordFields(),
        var homeFields: HomeFields = HomeFields()
)
{
    data class HomeFields(
            var newReleases: List<Comic>? = ArrayList()
    )

    data class ChangePasswordFields(
            var currentPassword: String? = null,
            var newPassword: String? = null,
            var newPasswordConfirmation: String? = null)
    {
        class ChangePasswordErrors {
            companion object {
                fun mustFillAllFields(): String {
                    return "All fields are required"
                }

                fun currentPasswordIsIncorrect(): String {
                    return "Your current password is incorrect"
                }

                fun passwordsDoNotMatch(): String {
                    return "The passwords you entered don't match"
                }

                fun none(): String {
                    return "None"
                }
            }
        }

        //TODO use this
        private fun validateChangePasswordFields(): String {
            if(currentPassword.isNullOrEmpty() || newPassword.isNullOrEmpty() || newPasswordConfirmation.isNullOrEmpty())
                return ChangePasswordErrors.mustFillAllFields()

            if(newPassword != newPasswordConfirmation)
                return ChangePasswordErrors.passwordsDoNotMatch()

            return ChangePasswordErrors.none()
        }
    }
}