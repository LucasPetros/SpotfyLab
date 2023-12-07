package com.lucas.petros.spotfylab.features.artists.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.lucas.petros.commons.base.BaseViewModel
import com.lucas.petros.commons.data.Constants.ACCESS_TOKEN
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.commons.utils.SecureTokenManager
import com.lucas.petros.spotfylab.features.artists.domain.model.Artist
import com.lucas.petros.spotfylab.features.artists.domain.use_case.GetArtistsTopUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistsViewModel @Inject constructor(
    private val useCase: GetArtistsTopUser,
    private val secureToken: SecureTokenManager,
) : BaseViewModel() {

    val pagingList = MutableLiveData<Flow<PagingData<Artist>>>()

    init {
        getArtists()
    }

    fun getArtists() {
        viewModelScope.launch {
            pagingList.value =
                useCase.execute(secureToken.getToken(ACCESS_TOKEN).handleOpt())
        }
    }

    private val _showLoading = MutableLiveData(true)
    val showLoading: LiveData<Boolean> = _showLoading

    fun showStopLoading(){
        _showLoading.value = false
    }
}