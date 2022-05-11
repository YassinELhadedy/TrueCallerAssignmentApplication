package com.example.truecallerassignmentapplication.infrastructure

import android.util.ArrayMap
import com.example.truecallerassignmentapplication.domain.GetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BlogsRepo @Inject constructor(private val service: TrueCallerWebService) :
    GetRepository<BlogGetApiComponent, String> {

    override fun get(apiComponent: BlogGetApiComponent): Flow<String> =
        when (apiComponent.first) {
            BlogDataSource.GET_TRUE_CALLER_BLOG ->  FlowHandler.flowDataWithErrorHandling {
                service.getTrueCallerBlogResponse()
            }
            else -> {throw InfrastructureException("API GET-WAY INVALID IN BlogDataSource")}
        }
}

enum class BlogDataSource(val value: Int) {
    GET_TRUE_CALLER_BLOG(1),
    GET_FACE_BOOK_BLOG(2),
    GET_TWITTER_BLOG(2);

    companion object {
        fun valueOf(value: Int) = values().find { it.value == value }
    }
}
typealias BlogGetApiComponent = Pair<BlogDataSource, ArrayMap<String,String>?>