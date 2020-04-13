package com.example.cleanfloyd.data.repository

import com.example.cleanfloyd.data.source.remote.AlbumsService
import com.example.cleanfloyd.data.source.remote.ReleasesResponse
import com.example.cleanfloyd.domain.model.Album
import com.example.cleanfloyd.schedulers.TestSchedulerProvider
import com.nhaarman.mockitokotlin2.any
import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.junit.After
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.Collections

@RunWith(MockitoJUnitRunner::class)
class AlbumsViewModelTest {
  private lateinit var albumsRepository: AlbumsRepository
  private val compositeDisposable = CompositeDisposable()
  @Mock private lateinit var albumsService: AlbumsService

  private val albumRelease = Album(title = "Californication", format = "LP, Album, Promo")
  private val otherRelease = Album(title = "Can't Stop", format = "Single, Promo")

  private val completeAlbums = Collections.nCopies(20, albumRelease)
  private val partialOther = Collections.nCopies(5, otherRelease)
  private val partialAlbums = Collections.nCopies(5, albumRelease)
  private val partialMixed: List<Album> = partialOther + partialAlbums

  private val completeResponse = ReleasesResponse(completeAlbums)
  private val mixedResponse = ReleasesResponse(partialMixed)
  private val otherResponse = ReleasesResponse(partialOther)

  @Before
  fun setUp() {
    albumsRepository = AlbumsRepository(albumsService)
  }

  @After
  fun tearDown() {
    verifyNoMoreInteractions(albumsService)
    compositeDisposable.clear()
  }

  @Test
  fun testGetAlbumsError() {
    `when`(albumsService.getAlbums(any())).thenReturn(Single.create {
      it.onError(Throwable())
    })
    var error: Throwable? = null
    compositeDisposable.add(
        albumsRepository.getAlbums(1)
            .subscribeOn(TestSchedulerProvider.io)
            .observeOn(TestSchedulerProvider.ui)
            .subscribe({}) {
          error = it
        })
    assertNotNull(error)
    verify(albumsService).getAlbums(1)
  }

  @Test
  fun testGetAlbumsComplete() {
    `when`(albumsService.getAlbums(any())).thenReturn(Single.create {
      it.onSuccess(completeResponse)
    })
    var error: Throwable? = null
    var albums: List<Album>? = null
    compositeDisposable.add(
        albumsRepository.getAlbums(1)
            .subscribeOn(TestSchedulerProvider.io)
            .observeOn(TestSchedulerProvider.ui)
            .subscribe({
              albums = it
            }) {
              error = it
            })
    assertNull(error)
    assertNotNull(albums)
    assertTrue(albums!!.size == 20)
    verify(albumsService).getAlbums(1)
  }

  @Test
  fun testGetAlbumsCompleteAfterRepeat() {
    `when`(albumsService.getAlbums(any())).thenReturn(Single.create {
      it.onSuccess(mixedResponse)
    })
    var error: Throwable? = null
    var albums: List<Album>? = null
    compositeDisposable.add(
        albumsRepository.getAlbums(1)
            .subscribeOn(TestSchedulerProvider.io)
            .observeOn(TestSchedulerProvider.ui)
            .subscribe({
              albums = it
            }) {
              error = it
            })
    assertNull(error)
    assertNotNull(albums)
    assertTrue(albums!!.size == 20)
    verify(albumsService).getAlbums(1)
    verify(albumsService).getAlbums(2)
    verify(albumsService).getAlbums(3)
    verify(albumsService).getAlbums(4)
  }

  @Test
  fun testGetAlbumsEmptyAfterMaxTraversal() {
    `when`(albumsService.getAlbums(any())).thenReturn(Single.create {
      it.onSuccess(otherResponse)
    })
    var error: Throwable? = null
    var albums: List<Album>? = null
    compositeDisposable.add(
        albumsRepository.getAlbums(3)
            .subscribeOn(TestSchedulerProvider.io)
            .observeOn(TestSchedulerProvider.ui)
            .subscribe({
              albums = it
            }) {
              error = it
            })
    assertNull(error)
    assertNotNull(albums)
    assertTrue(albums!!.isEmpty())
    verify(albumsService).getAlbums(3)
    verify(albumsService).getAlbums(4)
    verify(albumsService).getAlbums(5)
    verify(albumsService).getAlbums(6)
    verify(albumsService).getAlbums(7)
  }
}