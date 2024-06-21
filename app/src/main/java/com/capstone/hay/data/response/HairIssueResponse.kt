package com.capstone.hay.data.response

import com.google.gson.annotations.SerializedName

data class HairIssueResponse(

	@field:SerializedName("data")
	val data: List<DataItemResponse>,

	@field:SerializedName("error")
	val error: String? = null,
)

data class DataItemResponse(

	@field:SerializedName("treatment")
	val treatment: String,

	@field:SerializedName("product_type")
	val productType: String,

	@field:SerializedName("hair_issue")
	val hairIssue: String,

	@field:SerializedName("caused_by")
	val causedBy: String,

	@field:SerializedName("product_image")
	val productImage: String,

	@field:SerializedName("product_id")
	val productId: String,

	@field:SerializedName("rating")
	val rating: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("product_name")
	val productName: String,

	@field:SerializedName("brand")
	val brand: String,

	@field:SerializedName("key_ingredients")
	val keyIngredients: String,

	@field:SerializedName("hair_issue_def")
	val hairIssueDef: String,

	@field:SerializedName("_12")
	val jsonMember12: String? = null
)
