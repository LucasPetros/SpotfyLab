package com.lucas.petros.spotfylab.features.playlists.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lucas.petros.commons.base.BaseState
import com.lucas.petros.commons.base.BaseViewModel
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.commons.utils.Event
import com.lucas.petros.commons.utils.Prefs
import com.lucas.petros.commons.utils.Prefs.Companion.KEY_ACCESS_TOKEN
import com.lucas.petros.commons.utils.Prefs.Companion.KEY_USER_ID
import com.lucas.petros.commons.utils.resultResource
import com.lucas.petros.spotfylab.features.profile.domain.model.UserProfile
import com.lucas.petros.spotfylab.features.profile.domain.use_case.GetUserProfile
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
    private val prefs: Prefs,
) : BaseViewModel() {

    // PagingList - Section
    val pagingList = MutableLiveData<Flow<PagingData<Playlist>>>()

    // UserProfile - Section
    val stateUserProfile = MutableLiveData<BaseState<UserProfile>>()
    val imageProfile = stateUserProfile.map { it.data?.imageUrl.handleOpt() }

    // CreatePlaylist - Section
    val stateCreatePlaylist = MutableLiveData<BaseState<Boolean>>()
    val isLoadingCreate = stateCreatePlaylist.map { it.isLoading }

    init {
        getProfile()
    }

    fun createPlaylist(namePlaylist: String) {
        createPlaylist.invoke(
            prefs.getDecrypted(KEY_ACCESS_TOKEN).handleOpt(),
            prefs.getDecrypted(KEY_USER_ID).handleOpt(),
            namePlaylist
        ).onEach { result ->
            resultResource(result, stateCreatePlaylist)
        }.launchIn(viewModelScope)
    }

    private fun getProfile() {
        getUserProfile.invoke(prefs.getDecrypted(KEY_ACCESS_TOKEN).handleOpt()).onEach { result ->
            resultResource(result, stateUserProfile)
        }.launchIn(viewModelScope)
    }

    fun getPlaylists() {
        viewModelScope.launch {
            pagingList.value =
                useCase.execute(prefs.getDecrypted(KEY_ACCESS_TOKEN).handleOpt())
                    .cachedIn(viewModelScope)
        }
    }

    //Loading - Section

    private val _showLoading = MutableLiveData(true)
    val showLoading: LiveData<Boolean> = _showLoading

    fun showLoading(isLoading: Boolean) {
        _showLoading.value = isLoading
    }

    // OnClick Button - Section

    private val _onClickButton = MutableLiveData<Event<Boolean>>()
    val onClickButton: LiveData<Event<Boolean>> = _onClickButton

    fun onClickButton() {
        _onClickButton.value = Event(true)
    }
}