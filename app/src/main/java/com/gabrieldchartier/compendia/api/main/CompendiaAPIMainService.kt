package com.gabrieldchartier.compendia.api.main

import androidx.lifecycle.LiveData
import com.gabrieldchartier.compendia.models.AccountProperties
import com.gabrieldchartier.compendia.models.Comic
import com.gabrieldchartier.compendia.util.GenericAPIResponse
import retrofit2.http.*

interface CompendiaAPIMainService {

    @GET("accounts/properties")
    @FormUrlEncoded
    fun getAccountProperties(
            @Header("Authorization") authorization: String
    ): LiveData<GenericAPIResponse<AccountProperties>>

    @GET("new_releases/covers")
    @FormUrlEncoded
    fun getNewReleaseCovers(
            @Header("Authorization") authorization: String
    ): LiveData<GenericAPIResponse<Comic>>

}