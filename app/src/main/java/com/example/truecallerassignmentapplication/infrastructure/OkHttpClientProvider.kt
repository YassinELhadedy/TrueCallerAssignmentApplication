package com.example.truecallerassignmentapplication.infrastructure

import com.example.truecallerassignmentapplication.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class OkHttpClientProvider {
    private val CONTENT_ACCEPT_HEADER = "Accept"
    private val CONTENT_TYPE_HEADER = "Content-Type"
    private val AUTHORIZATION_HEADER = "Authorization"
    private val LANGUAGE_HEADER = "Accept-Language"
    private val APP_NAME_HEADER = "App-Name"
    private val API_VERSION_HEADER = "Api-Version"
    private val APP_VERSION_HEADER = "App-Version"
    private val COUNTRY_CODE_HEADER = "Country-Code"

    // header constant values

    private val CONTENT_TYPE = "application/json"
    private val CONTENT_ACCEPT = "text/plain"
    private val CONTENT_ACCEPT2 = "text/plain"


    val provideOkHttpClient: OkHttpClient by lazy {
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request()
                    val newRequest = request.newBuilder()
                        .addHeader(CONTENT_ACCEPT_HEADER, CONTENT_ACCEPT)
                        .addHeader(CONTENT_TYPE_HEADER, CONTENT_TYPE)
                        .build()
                    chain.proceed(newRequest)
                }
            httpClient.addInterceptor(loggingInterceptor)
                .callTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)

            httpClient.build()
        } else OkHttpClient
            .Builder()
            .build()
    }

    companion object {
        private const val CONNECT_TIMEOUT_IN_MS = 30000
    }
}