package com.lucas.petros.spotfylab.features.artists.presentation.detail_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lucas.petros.commons.base.BaseFragmentVDB
import com.lucas.petros.commons.extension.observeAndNavigateBack
import com.lucas.petros.spotfylab.R
import com.lucas.petros.spotfylab.databinding.FragmentAlbumsBinding
import com.lucas.petros.spotfylab.features.artists.presentation.detail_list.adapter.AlbumsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AlbumsFragment :
    BaseFragmentVDB<FragmentAlbumsBinding>(R.layout.fragment_albums) {

    private val vm: AlbumsViewModel by viewModels()
    private var albumsAdapter: AlbumsAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupRecyclerView()
    }

    override fun setupViewModel() {
        binding.vm = vm
    }

    override fun setupObservers() {
        observerAlbumsPaging()
        observeAndNavigateBack(vm.navigateBack)
    }

    private fun observerAlbumsPaging() {
        vm.pagingList.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                lifecycleScope.launch {
                    list.collectLatest {
                        albumsAdapter?.submitData(it)
                    }
                }
                vm.showStopLoading()
            }
        }
    }

    private fun setupAdapter() {
        albumsAdapter = AlbumsAdapter()
    }

    private fun setupRecyclerView() {
        binding.rvAlbums.adapter = albumsAdapter
    }
}