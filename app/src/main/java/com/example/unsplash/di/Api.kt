package com.example.unsplash.di

import com.example.unsplash.data.model.CollectionsItem
import com.example.unsplash.data.model.PhotosItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {

    @Headers("Authorization: Client-ID E7gzCjdD75eBEZ9lWXs45-VhoIXvbfYbwPva3PUyzps")
    @GET("photos/")
    suspend fun getPhotos(): Response<List<PhotosItem>>

    @Headers("Authorization: Client-ID E7gzCjdD75eBEZ9lWXs45-VhoIXvbfYbwPva3PUyzps")
    @GET("collections/")
    suspend fun getCollections(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Response<List<CollectionsItem>>

}