package com.capstone.hay.data.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(

	@field:SerializedName("totalItems")
	val totalItems: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("itemsPerPage")
	val itemsPerPage: Int? = null,

	@field:SerializedName("totalPages")
	val totalPages: Int? = null,

	@field:SerializedName("currentPage")
	val currentPage: Int? = null,

	@field:SerializedName("error")
	val error: String? = null,
)

data class UpdatedAt(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int? = null,

	@field:SerializedName("_seconds")
	val seconds: Int? = null
)

data class DataItem(

	@field:SerializedName("createdAt")
	val createdAt: CreatedAt,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("updatedAt")
	val updatedAt: UpdatedAt,

	@field:SerializedName("message")
	val message: String? = null
)
