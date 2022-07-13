package com.tarikmedjber.spacexclient.engine.network

sealed class RequestState<T> {
    data class Success<T>(val data: T) : RequestState<T>()
    data class Failure<T>(val message: String) : RequestState<T>()
    class Loading<T> : RequestState<T>()
}