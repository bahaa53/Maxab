package com.task.maxab.domain.repositories

sealed class Result<out T : Any> {
    class Success<out T : Any>(val data: T?) : Result<T>()

    class Error(
        val exception: Throwable = NoSuchMethodException(),
        val message: String = exception.localizedMessage
    ) :
        Result<Nothing>()
}