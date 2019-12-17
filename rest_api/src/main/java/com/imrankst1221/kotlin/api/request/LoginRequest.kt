package com.imrankst1221.kotlin.api.request
import com.google.gson.annotations.SerializedName
import com.imrankst1221.kotlin.api.response.LoginResponse
import io.reactivex.Observable
import retrofit2.http.*

// post request example
interface LoginApiService {
    @Headers("Content-Type: application/json")
    @POST("login")
    fun doLogin(
            //@Query("Authorization") authorizationKey: String, // authentication header
            @Body loginPostData: LoginPostData): Observable<LoginResponse> // body data
}

data class LoginPostData(
        @SerializedName("UserId") var userID: String,
        @SerializedName("Password") var userPassword: String
)
