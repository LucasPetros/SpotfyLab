package com.lucas.petros.spotfylab.features.playlists.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lucas.petros.commons.base.BaseState
import com.lucas.petros.commons.base.BaseViewModel
import com.lucas.petros.commons.data.Constants
import com.lucas.petros.commons.data.Constants.ACCESS_TOKEN
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.commons.utils.Event
import com.lucas.petros.commons.utils.SecureTokenManager
import com.lucas.petros.commons.utils.resultResource
import com.lucas.petros.spotfylab.features.login.domain.model.UserProfile
import com.lucas.petros.spotfylab.features.login.domain.use_case.GetUserProfile
import com.lucas.petros.spotfylab.features.playlists.domain.model.Playlist
import com.lucas.petros.spotfylab.features.playlists.domain.use_case.GetPlaylists
import com.lucas.petros.spotfylab.features.playlists.domain.use_case.PostCreatePlaylist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistsViewModel @Inject constructor(
    private val useCase: GetPlaylists,
    private val getUserProfile: GetUserProfile,
    private val createPlaylist: PostCreatePlaylist,
    private val secureToken: SecureTokenManager,
) : BaseViewModel() {

    val pagingList = MutableLiveData<Flow<PagingData<Playlist>>>()
    private val stateProfile = MutableLiveData<BaseState<UserProfile>>()
    val imageProfile = stateProfile.map { it.data?.imageUrl.handleOpt() }
    private val userId = stateProfile.map { it.data?.id }
    private val stateCreatePlaylist = MutableLiveData<BaseState<Boolean>>()
    val isLoadingCreate = stateCreatePlaylist.map { it.isLoading }

    init {
        getUserProfile()
        getPlaylists()
    }

    fun createPlaylist(name: String) {
        createPlaylist.invoke(
            secureToken.getToken(ACCESS_TOKEN).handleOpt(),
            userId.value.handleOpt(),
            name
        ).onEach { result ->
            resultResource(result, stateCreatePlaylist)
        }.launchIn(viewModelScope)
    }

    private fun getUserProfile() {
        getUserProfile.invoke(secureToken.getToken(ACCESS_TOKEN).handleOpt()).onEach { result ->
            resultResource(result, stateProfile)
        }.launchIn(viewModelScope)
    }

    private fun getPlaylists() {
        viewModelScope.launch {
            pagingList.value =
                useCase.execute(secureToken.getToken(ACCESS_TOKEN).handleOpt())
                    .cachedIn(viewModelScope)
        }
    }

    private val _showLoading = MutableLiveData(true)
    val showLoading: LiveData<Boolean> = _showLoading

    fun showLoading(isLoad: Boolean) {
        _showLoading.value = false
    }

    private val _onClickButton = MutableLiveData<Event<Boolean>>()
    val onClickButton: LiveData<Event<Boolean>> = _onClickButton

    fun onClickButton() {
        _onClickButton.value = Event(true)
    }
}