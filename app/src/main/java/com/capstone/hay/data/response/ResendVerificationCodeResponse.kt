package com.capstone.hay.data.response

import com.google.gson.annotations.SerializedName

data class ResendVerificationCodeResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("error")
	val error: String? = null
)
