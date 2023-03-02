package com.quiqprint.hub_android.models


import com.google.gson.annotations.SerializedName

data class SingleModel(
    @SerializedName("data")
    val data: List<SingleData>,
    @SerializedName("logout")
    val logout: Boolean,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("status")
    val status: Int
)

data class SingleData(
    @SerializedName("category")
    val category: String,
    @SerializedName("collections")
    val collections: Any,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("path")
    val path: String,
    @SerializedName("sharedWith")
    val sharedWith: Any,
    @SerializedName("size")
    val size: Double,
    @SerializedName("status")
    val status: Any,
    @SerializedName("type")
    val type: String
)