package com.lucas.petros.spotfylab.features.artists.presentation.artist_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.lucas.petros.commons.base.BaseFragmentVDB
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.commons.utils.Prefs
import com.lucas.petros.spotfylab.R
import com.lucas.petros.spotfylab.databinding.FragmentArtistsBinding
import com.lucas.petros.spotfylab.features.artists.presentation.artist_list.adapter.ArtistsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ArtistsFragment : BaseFragmentVDB<FragmentArtistsBinding>(R.layout.fragment_artists) {
    private val vm: ArtistsViewModel by viewModels()
    private var artistsAdapter: ArtistsAdapter? = null

    @Inject
    lateinit var prefs: Prefs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupRecyclerView()
        vm.getRefreshToken()
    }

    override fun setupViewModel() {
        binding.vm = vm
    }

    override fun setupObservers() {
        observerArtistsPaging()
        observerStateAccess()
    }

    private fun observerArtistsPaging() {
        vm.pagingList.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                lifecycleScope.launch {
                    list.collectLatest {
                        artistsAdapter?.submitData(it)
                    }
                }
                vm.showStopLoading()
            }
        }
    }

    private fun observerStateAccess() {
        vm.stateAccess.observe(viewLifecycleOwner) { dataAccess ->
            if (dataAccess.data?.accessToken?.isNotBlank() == true || dataAccess.error.isNotBlank()) {
                vm.getProfile(dataAccess.data?.accessToken.handleOpt())
                vm.getArtists(dataAccess.data?.accessToken.handleOpt())
            }
        }
    }

    private fun setupAdapter() {
        artistsAdapter = ArtistsAdapter { artist ->
            val direction = ArtistsFragmentDirections.toArtistDetailFragment()
            direction.id = artist?.id.handleOpt()
            direction.imageUrl = artist?.imageUrl.handleOpt()
            direction.name = artist?.name.handleOpt()
            findNavController().navigate(direction)
        }
    }

    private fun setupRecyclerView() {
        binding.rvArtists.adapter = artistsAdapter
    }
}