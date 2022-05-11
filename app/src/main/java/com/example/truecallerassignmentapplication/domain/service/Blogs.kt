package com.example.truecallerassignmentapplication.domain.service

import com.exa.nanashopper.domain.Condition
import com.exa.nanashopper.domain.Operator.Equal
import com.exa.nanashopper.domain.Pagination
import com.exa.nanashopper.domain.SortBy
import com.example.truecallerassignmentapplication.domain.Paginatee
import com.example.truecallerassignmentapplication.infrastructure.BlogDataSource
import com.example.truecallerassignmentapplication.infrastructure.BlogGetApiComponent
import com.example.truecallerassignmentapplication.infrastructure.BlogsRepo
import com.example.truecallerassignmentapplication.infrastructure.Paginator
import com.example.truecallerassignmentapplication.ui.blog.SEPARATOR
import com.example.truecallerassignmentapplication.ui.blog.SEPARATOR_DECORATOR
import com.example.truecallerassignmentapplication.ui.blog.SPACE_SEPARATOR
import kotlinx.coroutines.flow.*
import javax.inject.Inject

//class FeedAndDrops @Inject constructor(
//    private val feedRepo: FeedRepo,
//    private val dropRepo: DropRepo
//) {
////    fun getDrops(
////        dropGetApiComponent: DropGetApiComponent?,
////        flagEndPoint: DropGetApiComponent?,
////    ): Flow<PagingData<Any>> {
////        return (newFinancialRepo.get(
////            Pair(
////                flagEndPoint,
////                input
////            )
////        ) as Flow<PagingData<Any>>).cachedIn(viewModelScope)
////    }
//}

class QuereyBuilder {
    val map = mutableListOf<BlogDataSource>()
    fun andBuilder(lhs: BlogDataSource, rhs: BlogDataSource): BlogDataSource {
        map.add(lhs)
        map.add(rhs)
        return lhs
    }
}


class Blogs @Inject constructor(val blogsRepo: BlogsRepo) {

    fun getBlogs(pagination: Pagination): Flow<String> =
        Paginator(pagination,
            object : Paginatee<String, QuereyBuilder, BlogDataSource> {
                val qb = QuereyBuilder()
                override fun filter(expr: BlogDataSource?): QuereyBuilder {
                    return qb
                }

                override fun andExpr(lhs: BlogDataSource, rhs: BlogDataSource): BlogDataSource =
                    qb.andBuilder(lhs, rhs)


                override fun orExpr(lhs: BlogDataSource, rhs: BlogDataSource): BlogDataSource {
                    TODO("Not yet implemented")
                }

                override fun condition(condition: Condition<Any?>): BlogDataSource =
                    when (condition.field) {
                        "END_POINT" -> {
                            when (condition.operator) {
                                Equal -> when (condition.constant) {
                                    is BlogDataSource -> condition.constant
                                    else -> TODO()
                                }
                                else -> TODO()
                            }
                        }
                        else -> TODO()
                    }

                override fun sort(query: QuereyBuilder, sortBy: SortBy): QuereyBuilder {
                    TODO("Not yet implemented")
                }

                override fun limit(query: QuereyBuilder, limit: Int): QuereyBuilder {
                    return qb
                }

                override fun offset(query: QuereyBuilder, offset: Int): QuereyBuilder {
                    return qb
                }

                override fun run(query: QuereyBuilder): Flow<String> {
                    return blogsRepo.get(BlogGetApiComponent(query.map[0], null)).zip(
                        blogsRepo.get(BlogGetApiComponent(query.map[1], null))
                            .zip(
                                blogsRepo.get(
                                    BlogGetApiComponent(
                                        query.map[2],
                                        null
                                    )
                                )
                            ) { secondRequestItem, thirdRequestItem ->

                                val listOfEvery10thChar = secondRequestItem.chunked(10).mapNotNull {
                                    if (it.length == 10) {
                                        it[9]
                                    } else {
                                        null
                                    }
                                }

                                val occurrenceOfEveryUniqueWord =
                                    thirdRequestItem.toLowerCase().split(SPACE_SEPARATOR.toRegex())
                                        .groupBy { it }
                                        .map { "${it.key}=${it.value.size}" }.joinToString(
                                            SEPARATOR_DECORATOR
                                        )


                                "${listOfEvery10thChar.joinToString("-")}$SEPARATOR${occurrenceOfEveryUniqueWord}"

                            }) { firstRequestItem, secondAndThirdItem ->


                        val char10th = firstRequestItem.chunked(10).mapNotNull {
                            if (it.length == 10) {
                                it[9]
                            } else {
                                null
                            }
                        }[0]

                        "${char10th}$SEPARATOR${secondAndThirdItem}"
                    }.map {
                        it
                    }
                }
            }).run()

}
