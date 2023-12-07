package com.lucas.petros.spotfylab.features.artists.presentation.detail_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.lucas.petros.commons.base.BaseViewModel
import com.lucas.petros.commons.data.Constants
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.commons.utils.SecureTokenManager
import com.lucas.petros.spotfylab.features.artists.domain.model.Album
import com.lucas.petros.spotfylab.features.artists.domain.use_case.GetAlbumsByArtistId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    private val useCase:GetAlbumsByArtistId,
    private val secureToken: SecureTokenManager,
    state: SavedStateHandle,
) : BaseViewModel() {

    val args = AlbumsFragmentArgs.fromSavedStateHandle(state)
    val pagingList = MutableLiveData<Flow<PagingData<Album>>>()

    init {
        getAlbumsById(args.id)
    }


    private fun getAlbumsById(id:String) {
        viewModelScope.launch {
            pagingList.value =
                useCase.execute(secureToken.getToken(Constants.ACCESS_TOKEN).handleOpt(),id)
        }
    }

    private val _showLoading = MutableLiveData(true)
    val showLoading: LiveData<Boolean> = _showLoading

    fun showStopLoading(){
        _showLoading.value = false
    }
}