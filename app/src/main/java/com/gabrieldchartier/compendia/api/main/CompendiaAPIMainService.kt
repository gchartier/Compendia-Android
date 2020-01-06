package com.gabrieldchartier.compendia.api.main

import androidx.lifecycle.LiveData
import com.gabrieldchartier.compendia.api.GenericResponse
import com.gabrieldchartier.compendia.api.main.network_responses.ComicListResponse
import com.gabrieldchartier.compendia.models.AccountProperties
import com.gabrieldchartier.compendia.util.GenericAPIResponse
import retrofit2.http.*

interface CompendiaAPIMainService {

    @GET("accounts/properties")
    fun getAccountProperties(
            @Header("Authorization") authorization: String
    ): LiveData<GenericAPIResponse<AccountProperties>>

    @GET("new_releases")
    fun getNewReleases(
            @Header("Authorization") authorization: String
    ): LiveData<GenericAPIResponse<ComicListResponse>>

    @PUT("accounts/change_password/")
    @FormUrlEncoded
    fun changePassword(
            @Header("Authorization") authorization: String,
            @Field("old_password") currentPassword: String,
            @Field("new_password") newPassword: String,
            @Field("confirm_new_password") newPasswordConfirmation: String
    ): LiveData<GenericAPIResponse<GenericResponse>>

}