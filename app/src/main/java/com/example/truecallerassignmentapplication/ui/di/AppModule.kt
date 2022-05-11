package com.example.truecallerassignmentapplication.ui.di

import android.content.Context
import com.example.truecallerassignmentapplication.infrastructure.BlogsRepo
import com.example.truecallerassignmentapplication.infrastructure.TrueCallerWebService
import com.example.truecallerassignmentapplication.infrastructure.OkHttpClientProvider
import com.example.truecallerassignmentapplication.infrastructure.RetrofitFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideJumiaApi(@ApplicationContext context: Context): TrueCallerWebService {
        return RetrofitFactory(OkHttpClientProvider().provideOkHttpClient).apiService
    }

    @Singleton
    @Provides
    fun provideBlogsRepo(api: TrueCallerWebService) = BlogsRepo(api)
}