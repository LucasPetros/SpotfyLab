package com.lucas.petros.spotfylab.features.playlists.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lucas.petros.commons.base.BaseFragmentVDB
import com.lucas.petros.commons.extension.observeAndNavigateBack
import com.lucas.petros.spotfylab.R
import com.lucas.petros.spotfylab.databinding.FragmentPlaylistsBinding
import com.lucas.petros.spotfylab.features.playlists.presentation.adapter.PlaylistsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlaylistsFragment : BaseFragmentVDB<FragmentPlaylistsBinding>(R.layout.fragment_playlists),
    CreatePlaylistDialogFragment.DialogListener {
    private val vm: PlaylistsViewModel by viewModels()
    private var playlistsAdapter: PlaylistsAdapter? = null
    private val dialogFragment = CreatePlaylistDialogFragment()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupRecyclerView()
    }

    override fun setupViewModel() {
        binding.vm = vm
    }

    override fun setupObservers() {
        observerOnButtonClick()
        observerLoadingCreatePlaylist()
        observerPlaylistsPaging()
        observeAndNavigateBack(vm.navigateBack)
    }

    private fun showCustomDialog() {
        dialogFragment.setDialogListener(this)
        dialogFragment.show(requireActivity().supportFragmentManager, null)
    }

    private fun observerPlaylistsPaging() {
        vm.pagingList.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                lifecycleScope.launch {
                    list.collectLatest {
                        playlistsAdapter?.submitData(it)
                    }
                }
                vm.showLoading(false)
            }
        }
    }

    private fun observerOnButtonClick() {
        vm.onClickButton.observe(viewLifecycleOwner) { state ->
            state.getContentIfNotHandled()?.let { isClick ->
                if (isClick) {
                    showCustomDialog()
                }
            }
        }
    }

    private fun observerLoadingCreatePlaylist() {
        vm.isLoadingCreate.observe(viewLifecycleOwner) { isloading ->
            vm.showLoading(isloading)
        }
    }

    private fun setupAdapter() {
        playlistsAdapter = PlaylistsAdapter()
    }

    private fun setupRecyclerView() {
        binding.rvPlayLists.adapter = playlistsAdapter
    }

    override fun onCreateButtonClick(userInput: String) {
        vm.createPlaylist(userInput)
        dialogFragment.dialog?.cancel()
    }
}