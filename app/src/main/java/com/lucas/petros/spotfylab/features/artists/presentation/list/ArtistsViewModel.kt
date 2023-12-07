package com.lucas.petros.spotfylab.features.artists.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lucas.petros.commons.base.BaseState
import com.lucas.petros.commons.base.BaseViewModel
import com.lucas.petros.commons.data.Constants.ACCESS_TOKEN
import com.lucas.petros.commons.data.Constants.TOKEN_REFRESH
import com.lucas.petros.commons.data.DataResource
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.commons.utils.SecureTokenManager
import com.lucas.petros.commons.utils.resultResource
import com.lucas.petros.spotfylab.features.artists.domain.model.Artist
import com.lucas.petros.spotfylab.features.artists.domain.use_case.GetArtistsTopUser
import com.lucas.petros.spotfylab.features.login.domain.model.AccessToken
import com.lucas.petros.spotfylab.features.login.domain.model.UserProfile
import com.lucas.petros.spotfylab.features.login.domain.use_case.GetRefreshToken
import com.lucas.petros.spotfylab.features.login.domain.use_case.GetUserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistsViewModel @Inject constructor(
    private val useCase: GetArtistsTopUser,
    private val getNewToken: GetRefreshToken,
    private val getProfile: GetUserProfile,
    private val secureToken: SecureTokenManager,
) : BaseViewModel() {

    val pagingList = MutableLiveData<Flow<PagingData<Artist>>>()

    val stateToken = MutableLiveData<BaseState<AccessToken>>()
    private val stateUserProfile = MutableLiveData<BaseState<UserProfile>>()
    val image = stateUserProfile.map { it.data?.imageUrl }

    init {
        getNewToken()
        getProfile()
    }

    fun getArtists() {
        viewModelScope.launch {
            pagingList.value = useCase.execute(secureToken.getToken(ACCESS_TOKEN).handleOpt())
                .cachedIn(viewModelScope)
        }
    }

    private fun getProfile(){
        getProfile.invoke(secureToken.getToken(ACCESS_TOKEN).handleOpt()).onEach { result ->
            resultResource(result,stateUserProfile)
        }.launchIn(viewModelScope)
    }

    private fun getNewToken() {
        getNewToken.invoke(secureToken.getToken(TOKEN_REFRESH).handleOpt()).onEach { result ->
            when (result) {
                is DataResource.Loading -> {
                    stateToken.value = BaseState(isLoading = true)
                }

                is DataResource.Success -> {
                    stateToken.value = BaseState(isLoading = false, data = result.data)
                    secureToken.saveToken(ACCESS_TOKEN, result.data?.accessToken.handleOpt())
                }

                is DataResource.Error -> {
                    stateToken.value = BaseState(
                        error = result.message.handleOpt(),
                        data = result.data,
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private val _showLoading = MutableLiveData(true)
    val showLoading: LiveData<Boolean> = _showLoading

    fun showStopLoading() {
        _showLoading.value = false
    }
}