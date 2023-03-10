package com.example.unsplash.data

import com.example.unsplash.data.model.CollectionsItem
import com.example.unsplash.data.model.PhotosItem
import com.example.unsplash.di.Api
import com.example.unsplash.domain.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val api: Api) {

    fun getPhotos(): Flow<ResultOf<List<PhotosItem>>> {
        return flow {
            try {
                emit(ResultOf.Success(api.getPhotos().body()!!))
            } catch (e: HttpException) {
                emit(ResultOf.Failure(e))
            }
        }
    }

    suspend fun getCollection(page: Int, perPage: Int): Response<List<CollectionsItem>> {
        return api.getCollections(page, perPage)
    }
}