package com.yggdralisk.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
        @SerializedName("success") val success: Boolean,
        @SerializedName("data") val data: List<List<String>>,
        @SerializedName("error") val error: String?
)