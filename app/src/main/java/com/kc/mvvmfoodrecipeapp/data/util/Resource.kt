package com.kc.mvvmfoodrecipeapp.data.util

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
}
