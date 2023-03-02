package com.quiqprint.hub_android.models


import com.google.gson.annotations.SerializedName

data class PrintRequest(
    @SerializedName("data")
    val data: DataImage
)

data class DataImage(
    @SerializedName("id")
    val id: String
)