package com.example.truecallerassignmentapplication.domain

import kotlinx.coroutines.flow.Flow


/**
 * PutRepository
 */
interface PutRepository<in T> {
    fun update(entity: T): Flow<Unit>
}