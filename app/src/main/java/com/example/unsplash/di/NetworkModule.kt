package com.example.unsplash.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .client(okHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun okHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(okHttpLogging())
            .addInterceptor(provideAuthorizationInterceptor())
            .build()
    }

    private fun okHttpLogging(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private fun provideAuthorizationInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            request.newBuilder()
                .build()
            val response = chain.proceed(request)

            when (response.code) {

                200 -> {
                    Log.d("RESPONSE", "Everything worked as expected")
                }
                400 -> {
                    Log.d(
                        "RESPONSE",
                        "The request was unacceptable, often due to missing a required parameter"
                    )
                }
                401 -> {
                    Log.d("RESPONSE", "Invalid Access Token")
                }
                403 -> {
                    Log.d("RESPONSE", "Missing permissions to perform request")
                }
                404 -> {
                    Log.d("RESPONSE", "The requested resource doesn’t exist")
                }
                500, 503 -> {
                    Log.d("RESPONSE", "Something went wrong on our end")
                }
            }
            return@Interceptor response
        }
    }
}