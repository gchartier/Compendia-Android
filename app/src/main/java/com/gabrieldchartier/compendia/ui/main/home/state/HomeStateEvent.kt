package com.gabrieldchartier.compendia.ui.main.home.state

sealed class HomeStateEvent {
    class GetAccountPropertiesEvent: HomeStateEvent()

    data class UpdateAccountPropertiesEvent(val username: String): HomeStateEvent()

    data class ChangePasswordEvent(
            val currentPassword: String,
            val newPassword: String,
            val newPasswordConfirmation: String
    ): HomeStateEvent()

    class None: HomeStateEvent()
}