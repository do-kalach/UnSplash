package com.example.unsplash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.unsplash.data.RepositoryImpl
import com.example.unsplash.data.model.CollectionsItem
import com.example.unsplash.data.model.PhotosItem
import com.example.unsplash.domain.CollectionPagingSource
import com.example.unsplash.domain.ResultOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repositoryImpl: RepositoryImpl
) : ViewModel() {

    val state: Flow<ResultOf<List<PhotosItem>>> = repositoryImpl.getPhotos()
        .onStart { emit(ResultOf.Loading) }
        .flowOn(Dispatchers.IO)

    val listData = Pager(PagingConfig(pageSize = 10)) {
        CollectionPagingSource(repositoryImpl)
    }.flow.cachedIn(viewModelScope)

    suspend fun currentFlow(collection: String): Flow<ResultOf<List<PhotosItem>>> {
        return repositoryImpl.getCurrentCollections(collection)
            .onStart { emit(ResultOf.Loading) }
            .flowOn(Dispatchers.IO)
    }

}