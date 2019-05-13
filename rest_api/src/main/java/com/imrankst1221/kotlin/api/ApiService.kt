package com.imrankst1221.kotlin.api

import com.imrankst1221.kotlin.Constants
import com.imrankst1221.kotlin.api.request.LoginApiService
import com.imrankst1221.kotlin.api.request.UserApiService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


object ApiService {
    private val TAG = "--ApiService"

    // post request builder
    fun loginApiCall() = Retrofit.Builder()
            .baseUrl(Constants.API_BASE_PATH)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ApiWorker.gsonConverter)
            .client(ApiWorker.client)
            .build()
            .create(LoginApiService::class.java)!!

    // get request builder
    fun userApiCall() = Retrofit.Builder()
            .baseUrl(Constants.API_BASE_PATH)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ApiWorker.gsonConverter)
            .client(ApiWorker.client)
            .build()
            .create(UserApiService::class.java)!!
}