package com.imrankst1221.kotlin.api.request
import com.imrankst1221.kotlin.api.response.UserResponse
import io.reactivex.Observable
import retrofit2.http.*

// get request example
interface UserApiService {
    @Headers("Content-Type: application/json")
    @GET("user")
    fun getUser(
            @Query("Authorization") authorizationKey: String, // authentication header
            @Query("UserID") userID: String // authentication header
    ): Observable<UserResponse>
}


