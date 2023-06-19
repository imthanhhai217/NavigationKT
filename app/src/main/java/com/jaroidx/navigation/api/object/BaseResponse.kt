package com.jaroidx.navigation.api.`object`

sealed class BaseResponse<T>(
    val data: T? = null, val message: String? = null
) {
    class Success<T>(data: T?) : BaseResponse<T>(data)
    class Error<T>(data: T? = null, message: String?) : BaseResponse<T>(data, message)
    class Loading<T>() : BaseResponse<T>()
}
