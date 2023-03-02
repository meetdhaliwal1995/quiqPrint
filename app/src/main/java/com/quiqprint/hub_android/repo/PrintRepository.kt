package com.quiqprint.hub_android.repo

import com.quiqprint.hub_android.models.Data
import com.quiqprint.hub_android.models.DataImage
import com.quiqprint.hub_android.models.PrintRequest
import com.quiqprint.hub_android.models.SingleData
import com.quiqprint.hub_android.retrofit.MyApi
import kotlinx.coroutines.flow.*

class PrintRepository(private val myApi: MyApi) {

    fun getCommandData(id: String): Flow<List<Data?>?> {
        return flow {
            val response = myApi.postImagePrint(PrintRequest(DataImage(id)))

            if (response.isSuccessful) {
                println(response.body().toString())
                emit(response.body()?.data)
            }
        }
    }

    fun getSingleCommandData(id: String): Flow<List<SingleData>> {

        return flow {
            val responseSingle = myApi.postSingleImage(PrintRequest(DataImage(id)))

            if (responseSingle.isSuccessful) {
                emit(responseSingle.body()!!.data)
            }
        }

    }
}