package com.tarikmedjber.spacexclient.engine.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException

typealias ServiceCall<DateType> = suspend () -> Response<DateType>


interface RequestExecutor {
    fun <DataType> performRequest(serviceCall: ServiceCall<DataType>): Flow<RequestState<DataType>>
}

class RequestExecutorImpl : RequestExecutor {

    override fun <DataType> performRequest(serviceCall: ServiceCall<DataType>) =
        flow<RequestState<DataType>> {
            emit(RequestState.Loading())
            try {
                val response = serviceCall()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    emit(RequestState.Success(body))
                } else {
                    emit(RequestState.Failure(response.message()))
                }
            } catch (e: Exception) {
                when (e) {
                    is ConnectException, is UnknownHostException, is InterruptedIOException -> {
                        emit(RequestState.Failure(e.message ?: "Unknown error"))
                    }
                    else -> throw e
                }
            }

        }.flowOn(Dispatchers.IO)

}

