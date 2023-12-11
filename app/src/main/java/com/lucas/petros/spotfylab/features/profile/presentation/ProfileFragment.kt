package com.lucas.petros.spotfylab.features.profile.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.lucas.petros.commons.base.BaseFragmentVDB
import com.lucas.petros.spotfylab.R
import com.lucas.petros.spotfylab.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragmentVDB<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val vm: ProfileViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setupViewModel() {
        binding.vm = vm
    }

    override fun setupObservers() {

    }
}