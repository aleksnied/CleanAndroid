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

  init {
    loading.value = false
    empty.value = false
    error.value = null
  }

  fun loadAlbums() {
    loading.value = true
    getAlbumUseCase.execute(onOnSuccess = {
      loading.value = false
      albums.value = it
      empty.value = it.isEmpty()
      error.value = null
    }, onOnError = {
      loading.value = false
      empty.value = false
      error.value = it
    })
  }
}
