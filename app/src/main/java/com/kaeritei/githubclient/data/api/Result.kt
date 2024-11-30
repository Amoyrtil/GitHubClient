package com.kaeritei.githubclient.data.api

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()

    data class Error(val exception: Throwable) : Result<Nothing>()
}

/**
 * Successの結果を返します
 * Errorの場合はthrowします
 */
fun <T> Result<T>.successOrThrow(): T {
    return when (this) {
        is Result.Success -> data
        is Result.Error -> throw exception
    }
}
