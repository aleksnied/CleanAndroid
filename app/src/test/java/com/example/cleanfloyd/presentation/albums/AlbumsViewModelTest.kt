package com.example.cleanfloyd.presentation.albums

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cleanfloyd.domain.model.Album
import com.example.cleanfloyd.domain.usecase.GetAlbumsUseCase
import com.example.cleanfloyd.domain.usecase.base.OnError
import com.example.cleanfloyd.domain.usecase.base.OnSuccess
import com.nhaarman.mockitokotlin2.any
import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.nhaarman.mockitokotlin2.argumentCaptor
import org.mockito.Mock
import com.nhaarman.mockitokotlin2.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class AlbumsViewModelTest {
  private lateinit var albumsViewModel: AlbumsViewModel
  @Mock private lateinit var getAlbumsUseCase: GetAlbumsUseCase
  private val onSuccess = argumentCaptor<OnSuccess<List<Album>>>()
  private val onError = argumentCaptor<OnError>()
  private val albums = listOf(Album("Kingdoms in Colour"))

  @get:Rule val rule = InstantTaskExecutorRule()

  @Before
  fun setUp() {
    albumsViewModel = AlbumsViewModel(getAlbumsUseCase)
    assertNotNull(albumsViewModel.loading.value)
    assertFalse(albumsViewModel.loading.value!!)
    assertNotNull(albumsViewModel.empty.value)
    assertFalse(albumsViewModel.empty.value!!)
    assertNull(albumsViewModel.error.value)
  }

  @Test
  fun testLoadAlbumsError() {
    albumsViewModel.loadAlbums()
    verify(getAlbumsUseCase).execute(onSuccess.capture(), onError.capture(), any())
    assertNotNull(albumsViewModel.loading.value)
    assertTrue(albumsViewModel.loading.value!!)
    assertNotNull(albumsViewModel.empty.value)
    assertFalse(albumsViewModel.empty.value!!)
    assertNull(albumsViewModel.error.value)
    assertNull(albumsViewModel.albums.value)
    onError.lastValue.invoke(Throwable())
    assertNotNull(albumsViewModel.loading.value)
    assertFalse(albumsViewModel.loading.value!!)
    assertNotNull(albumsViewModel.empty.value)
    assertFalse(albumsViewModel.empty.value!!)
    assertNotNull(albumsViewModel.error.value)
    assertNull(albumsViewModel.albums.value)
  }

  @Test
  fun testLoadAlbumsEmpty() {
    albumsViewModel.loadAlbums()
    verify(getAlbumsUseCase).execute(onSuccess.capture(), onError.capture(), any())
    assertNotNull(albumsViewModel.loading.value)
    assertTrue(albumsViewModel.loading.value!!)
    assertNotNull(albumsViewModel.empty.value)
    assertFalse(albumsViewModel.empty.value!!)
    assertNull(albumsViewModel.error.value)
    assertNull(albumsViewModel.albums.value)
    onSuccess.lastValue.invoke(Collections.emptyList())
    assertNotNull(albumsViewModel.loading.value)
    assertFalse(albumsViewModel.loading.value!!)
    assertNotNull(albumsViewModel.empty.value)
    assertTrue(albumsViewModel.empty.value!!)
    assertNull(albumsViewModel.error.value)
    assertNotNull(albumsViewModel.albums.value)
    assertTrue(albumsViewModel.albums.value!!.isEmpty())
  }

  @Test
  fun testLoadAlbumsSuccess() {
    albumsViewModel.loadAlbums()
    verify(getAlbumsUseCase).execute(onSuccess.capture(), onError.capture(), any())
    assertNotNull(albumsViewModel.loading.value)
    assertTrue(albumsViewModel.loading.value!!)
    assertNotNull(albumsViewModel.empty.value)
    assertFalse(albumsViewModel.empty.value!!)
    assertNull(albumsViewModel.error.value)
    assertNull(albumsViewModel.albums.value)
    onSuccess.lastValue.invoke(albums)
    assertNotNull(albumsViewModel.loading.value)
    assertFalse(albumsViewModel.loading.value!!)
    assertNotNull(albumsViewModel.empty.value)
    assertFalse(albumsViewModel.empty.value!!)
    assertNull(albumsViewModel.error.value)
    assertNotNull(albumsViewModel.albums.value)
    assertTrue(albumsViewModel.albums.value!!.isNotEmpty())
  }
}