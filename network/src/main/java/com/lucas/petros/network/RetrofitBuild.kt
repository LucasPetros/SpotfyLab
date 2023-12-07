package com.lucas.petros.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val SPOTIFY_API_BASE_URL = "https://api.spotify.com/"
const val SPOTIFY_AUTH_URL = "https://accounts.spotify.com/"


fun getRetrofitAuth(): Retrofit = Retrofit.Builder()
    .baseUrl(SPOTIFY_AUTH_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(getOkHttpClient())
    .build()

fun getRetrofit(): Retrofit = Retrofit.Builder()
    .baseUrl(SPOTIFY_API_BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(getOkHttpClient())
    .build()