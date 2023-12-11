package com.lucas.petros.spotfylab.features.profile.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.lucas.petros.commons.base.BaseState
import com.lucas.petros.commons.base.BaseViewModel
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.commons.utils.Prefs
import com.lucas.petros.commons.utils.Prefs.Companion.KEY_ACCESS_TOKEN
import com.lucas.petros.commons.utils.resultResource
import com.lucas.petros.spotfylab.features.profile.domain.model.UserProfile
import com.lucas.petros.spotfylab.features.profile.domain.use_case.GetUserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: GetUserProfile,
    private val prefs: Prefs
) : BaseViewModel() {
    // State User Profile - Section
    val stateUserProfile = MutableLiveData<BaseState<UserProfile>>()
    val showLoading = stateUserProfile.map { it.isLoading }
    val image = stateUserProfile.map { it.data?.imageUrl }
    val username = stateUserProfile.map { it.data?.displayName }

    init {
        getUserProfile()
    }

    private fun getUserProfile() {
        useCase(prefs.getDecrypted(KEY_ACCESS_TOKEN).handleOpt()).onEach { result ->
            resultResource(result, stateUserProfile)
        }.launchIn(viewModelScope)
    }
}