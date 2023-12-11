package com.lucas.petros.spotfylab.features.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.lucas.petros.commons.base.BaseState
import com.lucas.petros.commons.utils.Event
import com.lucas.petros.commons.utils.resultResource
import com.lucas.petros.spotfylab.features.login.domain.model.AccessToken
import com.lucas.petros.spotfylab.features.login.domain.use_case.GetAuthorizationRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getAuthorization: GetAuthorizationRequest,
) : ViewModel() {

    // StateResource - Section
    val stateAccess = MutableLiveData<BaseState<AccessToken>>()
    val stateToken = stateAccess.map { it.data }

    fun getAccessToken(code:String){
        getAuthorization.invoke(code).onEach { result ->
            resultResource(result,stateAccess)
        }.launchIn(viewModelScope)
    }

    // OnClick Button - Section

    fun onClickButtonLogin() {
        _btnLoginState.value = Event(true)
    }

    // Show Loading - Section

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _showLoading

    private val _btnLoginState = MutableLiveData<Event<Boolean>>()
    val btnLoginState: LiveData<Event<Boolean>> = _btnLoginState

    fun showLoading(isLoading: Boolean){
        _showLoading.value = isLoading
    }
}