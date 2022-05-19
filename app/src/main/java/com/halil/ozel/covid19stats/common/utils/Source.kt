package com.halil.ozel.covid19stats.common.utils

data class Source<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {
    companion object {
        fun <T> success(data: T?): Source<T> {
            return Source(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> loading(data: T?): Source<T> {
            return Source(
                Status.LOADING,
                data,
                null
            )
        }

        fun <T> error(msg: String, data: T?): Source<T> {
            return Source(Status.ERROR, data, msg)
        }
    }
}