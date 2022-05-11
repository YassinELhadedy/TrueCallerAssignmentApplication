package com.example.truecallerassignmentapplication.infrastructure


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object FlowHandler {
    inline fun <reified T> flowDataWithErrorHandling(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        crossinline serviceCall: suspend () -> AppResponse
    ): Flow<T> =
        flow {
            val service = serviceCall()
            if (!service.status) {
                throw InfrastructureException("service not found")
            }
            emit(Gson().fromJson<T>(service.getResult(), object : TypeToken<T>() {}.type))
        }.catch { e ->
            throw InfrastructureException(e)
        }.flowOn(dispatcher) as Flow<T>

    inline fun flowStatusDataWithErrorHandling(dispatcher: CoroutineDispatcher = Dispatchers.IO, crossinline serviceCall: suspend () -> AppResponse): Flow<Boolean> =
        flow {
            val service = serviceCall()
            if (!service.status) {
                throw InfrastructureException(service.message)
            }
            emit(true)
        }.catch { e ->
            throw InfrastructureException(e)
        }.flowOn(dispatcher)

}

