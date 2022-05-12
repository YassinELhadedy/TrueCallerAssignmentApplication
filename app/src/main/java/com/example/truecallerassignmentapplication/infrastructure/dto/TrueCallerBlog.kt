package com.example.truecallerassignmentapplication.infrastructure.dto

import com.example.truecallerassignmentapplication.domain.model.Blog


data class TrueCallerBlog(
    var blogContent: String
) {
    companion object {
        fun Blog.toTrueCallerBlog() = TrueCallerBlog(content)
    }

    fun toBlog() = Blog(blogContent)
}