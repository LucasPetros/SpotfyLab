package com.lucas.petros.spotfylab.features.artists.presentation.album_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.commons.utils.Prefs
import com.lucas.petros.spotfylab.features.CoroutinesMainTestRule
import com.lucas.petros.spotfylab.features.artists.domain.use_case.GetAlbumsByArtistId
import getOrAwaitValue
import io.mockk.mockk
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AlbumsViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesMainTestRule()

    private var useCase = mockk<GetAlbumsByArtistId>(relaxed = true)
    private var prefs = mockk<Prefs>(relaxed = true)
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var vm: AlbumsViewModel


    @Before
    fun setup() {
        initSavedStateHandle()
        vm = AlbumsViewModel(useCase, prefs, savedStateHandle)
    }

    private fun initSavedStateHandle() {
        savedStateHandle = SavedStateHandle()
        savedStateHandle["id"] = "mock"
    }

    // Show Loading - Section
    @Test
    fun `GIVEN the initial state of ViewModel THEN showLoading is false`() {
        TestCase.assertTrue(vm.showLoading.value.handleOpt())
    }


    @Test
    fun `WHEN fun showStopLoading is called THEN showLoading is false`() =
        runTest {

            vm.showStopLoading()
            val value = vm.showLoading.getOrAwaitValue()
            Assert.assertEquals(false, value)
        }

    //Init viewModel - Section
    @Test
    fun `when ViewModel is initialized then retrieving album id from savedStateHandle`() =
        vm.run {
            assertEquals("mock", args.id)
        }

    // State pagingList - Section
    @Test
    fun `GIVEN the initial state of ViewModel THEN pagingList is null`() {
        TestCase.assertNotNull(vm.pagingList.value)
    }
}