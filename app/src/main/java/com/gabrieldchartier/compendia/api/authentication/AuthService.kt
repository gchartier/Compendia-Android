package com.gabrieldchartier.compendia.api.authentication

import androidx.lifecycle.LiveData
import com.gabrieldchartier.compendia.api.authentication.network_responses.LoginResponse
import com.gabrieldchartier.compendia.api.authentication.network_responses.RegistrationResponse
import com.gabrieldchartier.compendia.util.GenericAPIResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthenticationService {
    @POST("accounts/login")
    @FormUrlEncoded
    fun login(
            @Field("username") email: String,
            @Field("password") password: String
    ): LiveData<GenericAPIResponse<LoginResponse>>

    @POST("accounts/register")
    @FormUrlEncoded
    fun register(
            @Field("email") email: String,
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("password_confirmation") password_confirmation: String
    ): LiveData<GenericAPIResponse<RegistrationResponse>>
}