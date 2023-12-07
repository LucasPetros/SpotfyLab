package com.lucas.petros.spotfylab.features.login.data.remote.service

import com.lucas.petros.spotfylab.BuildConfig.CLIENT_ID
import com.lucas.petros.spotfylab.BuildConfig.SECRET_ID
import com.lucas.petros.spotfylab.features.login.data.Constants.REDIRECT_URI
import com.lucas.petros.spotfylab.features.login.data.remote.dto.AccessTokenDto
import okhttp3.Credentials
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST


interface LoginApi {

    @FormUrlEncoded
    @POST("api/token")
    suspend fun getAccessToken(
        @Header("Authorization") auth: String = Credentials.basic(
            CLIENT_ID,
            SECRET_ID
        ),
        @Header("Content-Type") content: String = "application/x-www-form-urlencoded",
        @Field("code") code: String?,
        @Field("redirect_uri") redirectUri: String = REDIRECT_URI,
        @Field("grant_type") grantType: String? = "authorization_code"
    ): AccessTokenDto
}