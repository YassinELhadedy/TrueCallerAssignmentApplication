package com.example.truecallerassignmentapplication.domain

import kotlinx.coroutines.flow.Flow

/**
 * DeleteRepository
 */
interface DeleteRepository {
    fun delete(id: Int) : Flow<Unit>
}