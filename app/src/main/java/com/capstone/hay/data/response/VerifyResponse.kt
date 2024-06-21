package com.capstone.hay.data.response

import com.google.gson.annotations.SerializedName

data class VerifyResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("error")
	val error: String? = null
)
