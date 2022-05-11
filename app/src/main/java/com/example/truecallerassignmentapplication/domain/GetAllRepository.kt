package com.example.truecallerassignmentapplication.domain

import kotlinx.coroutines.flow.Flow

/**
 * GetAllRepository
 */
interface GetAllRepository<out T> {
//    suspend fun getAll(pagination: Pagination): List<T>
     fun getAllWithPagination(): Flow<T>
}