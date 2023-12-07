package com.lucas.petros.commons.utils

import androidx.lifecycle.MutableLiveData
import com.lucas.petros.commons.base.BaseState
import com.lucas.petros.commons.data.DataResource
import com.lucas.petros.commons.extension.handleOpt

fun <T> resultResource(result: DataResource<T>, state: MutableLiveData<BaseState<T>>) {
    when (result) {
        is DataResource.Loading -> {
            state.value = BaseState(isLoading = true)
        }

        is DataResource.Success -> {
            state.value = BaseState(isLoading = false, data = result.data)
        }

        is DataResource.Error -> {
            state.value = BaseState(
                error = result.message.handleOpt(),
                data = result.data,
                isLoading = false
            )
        }
    }
}