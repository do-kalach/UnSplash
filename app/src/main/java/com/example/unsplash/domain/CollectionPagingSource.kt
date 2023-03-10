package com.example.unsplash.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.unsplash.data.RepositoryImpl
import com.example.unsplash.data.model.CollectionsItem
import javax.inject.Inject

class CollectionPagingSource @Inject constructor(private val repositoryImpl: RepositoryImpl) :
    PagingSource<Int, CollectionsItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CollectionsItem> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = repositoryImpl.getCollection(currentLoadingPageKey, params.loadSize)
            val responseData = mutableListOf<CollectionsItem>()
            val data = response.body() ?: emptyList()
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CollectionsItem>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}