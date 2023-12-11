package com.lucas.petros.spotfylab.features.artists.presentation.artist_list

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lucas.petros.commons.base.BaseState
import com.lucas.petros.commons.base.BaseViewModel
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.commons.utils.Prefs
import com.lucas.petros.commons.utils.Prefs.Companion.KEY_TOKEN_REFRESH
import com.lucas.petros.commons.utils.resultResource
import com.lucas.petros.spotfylab.features.artists.domain.model.Artist
import com.lucas.petros.spotfylab.features.artists.domain.use_case.GetArtistsTopUser
import com.lucas.petros.spotfylab.features.login.domain.model.AccessToken
import com.lucas.petros.spotfylab.features.profile.domain.model.UserProfile
import com.lucas.petros.spotfylab.features.login.domain.use_case.GetRefreshToken
import com.lucas.petros.spotfylab.features.profile.domain.use_case.GetUserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistsViewModel @Inject constructor(
    private val useCase: GetArtistsTopUser,
    private val getRefreshToken: GetRefreshToken,
    private val getProfile: GetUserProfile,
    private val prefs: Prefs,
) : BaseViewModel() {


    // State Access - Section
    private val state = MutableLiveData<BaseState<AccessToken>>()
    val stateAccess = state.map { it }

    // State User Profile - Section
    val stateUserProfile = MutableLiveData<BaseState<UserProfile>>()
    val image = stateUserProfile.map { it.data?.imageUrl }

    // State pagingList - Section
    val pagingList = MutableLiveData<Flow<PagingData<Artist>>>()


    fun getArtists(auth: String) {
        viewModelScope.launch {
            pagingList.value = useCase.execute(auth)
                .cachedIn(viewModelScope)
        }
    }

    fun getProfile(auth: String) {
        getProfile.invoke(auth).onEach { result ->
            resultResource(result, stateUserProfile)
        }.launchIn(viewModelScope)
    }

    fun getRefreshToken() {
        getRefreshToken.invoke(prefs.getDecrypted(KEY_TOKEN_REFRESH).handleOpt()).onEach { result ->
            resultResource(result, state)
        }.launchIn(viewModelScope)
    }

    // Show Loading - Section

    private val _showLoading = MutableLiveData(true)
    val showLoading: LiveData<Boolean> = _showLoading

    fun showStopLoading() {
        _showLoading.value = false
    }
}