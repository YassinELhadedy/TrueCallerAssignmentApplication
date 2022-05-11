package com.example.truecallerassignmentapplication.domain

import kotlinx.coroutines.flow.Flow

/**
 * GetRepository
 */
interface GetRepository<in U, out T> {
    fun get(apiComponent: U): Flow<T>
}
interface GetGetRepositoryWithId<out T>{
     fun get(id: String): Flow<T>

}