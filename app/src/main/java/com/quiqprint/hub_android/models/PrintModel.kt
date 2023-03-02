package com.quiqprint.hub_android.models


import com.google.gson.annotations.SerializedName

data class PrintModel(
    @SerializedName("data")
    val data: List<Data>,
    @SerializedName("logout")
    val logout: Boolean,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("status")
    val status: Int
)

data class Data(
    @SerializedName("collections")
    val collections: List<String>,
    @SerializedName("commandType")
    val commandType: String,
    @SerializedName("hub")
    val hub: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("singles")
    val singles: List<String>,
    @SerializedName("status")
    val status: Boolean
)