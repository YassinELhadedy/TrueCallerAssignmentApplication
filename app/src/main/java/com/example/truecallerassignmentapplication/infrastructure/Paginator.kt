package com.example.truecallerassignmentapplication.infrastructure

import com.exa.nanashopper.domain.*
import com.example.truecallerassignmentapplication.domain.Paginatee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

const val OFFSET_NEGATIVE = "offset cannot be negative."
const val PAGE_SIZE_NEGATIVE = "pageSize cannot be negative."

class Paginator<T, Query, Cond>(private val pagination: Pagination,
                                private val paginatee: Paginatee<T, Query, Cond>
) {

    private fun handleExpr(expr: Expr): Cond = when (expr) {
        is AndExpr -> paginatee.andExpr(handleExpr(expr.lhs), handleExpr(expr.rhs))
        is OrExpr -> paginatee.orExpr(handleExpr(expr.lhs), handleExpr(expr.rhs))
        is Condition<Any?> -> paginatee.condition(expr)
    }

    fun run(): Flow<T> =
            flow<T> {
                if (pagination.offset < 0) {
                    throw IllegalArgumentException(OFFSET_NEGATIVE)
                }

                if (pagination.pageSize < 0) {
                    throw IllegalArgumentException(PAGE_SIZE_NEGATIVE)
                }

                val filter = paginatee.filter(pagination.filter?.let { handleExpr(it) })
                val sortBy = if (pagination.sort == null) {
                    filter
                } else {
                    paginatee.sort(filter, pagination.sort)
                }
                val offset = paginatee.offset(sortBy, pagination.offset)
                paginatee.run(paginatee.limit(offset, pagination.pageSize))
            }.flowOn(Dispatchers.IO)
}