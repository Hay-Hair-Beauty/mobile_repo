package com.capstone.hay.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("userData")
	val userData: UserData,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("token")
	val token: String,

	@field:SerializedName("error")
	val error: String? = null,
)


data class UserData(

	@field:SerializedName("createdAt")
	val createdAt: CreatedAt,

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("verified")
	val verified: Boolean,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("verificationCode")
	val verificationCode: Any? = null
)
