package com.quiqprint.hub_android.retrofit

import com.quiqprint.hub_android.helper.Constant
import com.quiqprint.hub_android.models.DataImage
import com.quiqprint.hub_android.models.PrintModel
import com.quiqprint.hub_android.models.PrintRequest
import com.quiqprint.hub_android.models.SingleModel
import retrofit2.http.*
import retrofit2.Response

/**
 * Network API Interface
 */
interface MyApi {

    @POST("/api/v1/command/filter")
    suspend fun postImagePrint(@Body printRequest: PrintRequest): Response<PrintModel>

    @POST("/api/v1/single/filter")
    suspend fun postSingleImage(@Body printRequest: PrintRequest): Response<SingleModel>

}

