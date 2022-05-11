package com.example.truecallerassignmentapplication.infrastructure

import com.example.truecallerassignmentapplication.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class RetrofitFactory(private val okHttpClient: OkHttpClient) {
    val gson = GsonBuilder()
        .setLenient()
        .create()

    val apiService: TrueCallerWebService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.TRUE_CALLER_BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())

//            .client(okHttpClient)//Remove this and replace it with okHttpClient
            .build().create(TrueCallerWebService::class.java)
    }
}
