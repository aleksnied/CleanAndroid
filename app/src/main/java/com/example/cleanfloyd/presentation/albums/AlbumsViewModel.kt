package com.example.cleanfloyd.presentation.albums

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleanfloyd.domain.model.Album
import com.example.cleanfloyd.domain.usecase.GetAlbumsUseCase
import javax.inject.Inject

class AlbumsViewModel @Inject constructor(private val getAlbumUseCase: GetAlbumsUseCase) : ViewModel() {
  val loading = MutableLiveData<Boolean>()
  val empty = MutableLiveData<Boolean>()
  val error = MutableLiveData<Throwable>()
  val albums = MutableLiveData<List<Album>>()
  var currentPage = 1

  init {
    loading.value = false
    empty.value = false
    error.value = null
  }

  fun loadAlbums() {
    loading.value = true
    getAlbumUseCase.currentPage = currentPage
    getAlbumUseCase.execute(onOnSuccess = {
      loading.value = false
      albums.value = it.first
      empty.value = it.first.isEmpty()
      error.value = null
      currentPage = it.second
    }, onOnError = {
      loading.value = false
      empty.value = false
      error.value = it
    })
  }
}
