package com.example.truecallerassignmentapplication.infrastructure

import retrofit2.http.*

interface TrueCallerWebService {

    @GET("/2018/01/22/life-as-an-android-engineer/")
    suspend fun getTrueCallerBlogResponse(): String
}