package com.imrankst1221.kotlin.api.response
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserResponse(
		@SerializedName("name") val name: String = "",
		@SerializedName("email") val email: String = "",
		@SerializedName("phone") val phone: String = "",

		@SerializedName("error") val error: String = "",
		@SerializedName("error_type") val errorType: String = "",

		@Expose(deserialize = false) // deserialize is this filed is not required
		@SerializedName("message") val message: String = ""
)
