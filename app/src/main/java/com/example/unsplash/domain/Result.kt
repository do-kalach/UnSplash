package com.example.unsplash.domain

sealed class ResultOf<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResultOf<T>()
    data class Failure(val exception: Exception) : ResultOf<Nothing>()
    object Loading : ResultOf<Nothing>()
}