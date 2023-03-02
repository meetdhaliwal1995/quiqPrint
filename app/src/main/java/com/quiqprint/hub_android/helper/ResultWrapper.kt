package com.example.interviewtask.helper

import androidx.annotation.Keep
import com.quiqprint.hub_android.helper.NetworkHelper

/**
 * Network response wrapper for handling Success and Failure response types
 */
sealed class ResultWrapper<out T> {

    @Keep
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    @Keep
    data class GenericError(val code: Int? = null, val error: NetworkHelper.ErrorResponse? = null) : ResultWrapper<Nothing>()
}