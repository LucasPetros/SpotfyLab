package com.lucas.petros.commons.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class DataResource<T>(
    private val coroutineScope: CoroutineScope
) {

    private val _callStarter = MutableLiveData<Unit>()

    private val resource = _callStarter.switchMap { loadResource() }

    val showLoader = resource.map { it is Result.Loading }

    val data: LiveData<T?> = resource.map { (it as? Result.Success)?.data }
    val showData = data.map { it != null }

    val error = resource.map { (it as? Result.Error)?.errorData }
    val showError = error.map { it != null }

    fun loadData() {
        _callStarter.value = Unit
    }

    private fun loadResource(): LiveData<Result<T>> {
        val result = MutableLiveData<Result<T>>()
        result.value = Result.Loading()
        coroutineScope.launch {
            try {
                result.value = Result.Success(resource())
            } catch (error: Throwable) {
                result.value = Result.Error(error)
            }
        }
        return result
    }

    protected abstract suspend fun resource(): T
}

private sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val errorData: Throwable) : Result<T>()
    class Loading<T> : Result<T>()
}

fun <T> resource(
    coroutineScope: CoroutineScope,
    block: suspend () -> T
) = object : DataResource<T>(coroutineScope) {
    override suspend fun resource() = block()
}
