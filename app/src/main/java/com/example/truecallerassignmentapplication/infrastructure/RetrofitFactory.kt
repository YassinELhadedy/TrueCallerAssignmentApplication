package com.example.truecallerassignmentapplication.infrastructure

import com.example.truecallerassignmentapplication.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitFactory(private val okHttpClient: OkHttpClient) {

    val apiService: TrueCallerWebService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.TRUE_CALLER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)//Remove this and replace it with okHttpClient
            .build().create(TrueCallerWebService::class.java)
    }
}
