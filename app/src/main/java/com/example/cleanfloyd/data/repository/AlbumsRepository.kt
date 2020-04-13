package com.example.cleanfloyd.data.repository

import com.example.cleanfloyd.data.source.remote.AlbumsService
import com.example.cleanfloyd.domain.model.Album
import io.reactivex.Single
import java.util.*

class AlbumsRepository(private val albumsService: AlbumsService) {

  fun getAlbums(page: Int = 1): Single<List<Album>> {
    var totalAlbums = 0
    var currentPage = page
    return Single.create<Int> {
      it.onSuccess(currentPage)
    }.flatMap { p ->
      albumsService.getAlbums(p).map {
        it.releases.filter { release ->
          !release.format.isNullOrEmpty() && release.format.toLowerCase(Locale.getDefault())
              .contains("album")
        }
      }
    }.flatMap { albums ->
      totalAlbums += albums.size
      currentPage += 1
      Single.just(albums)
    }.repeatUntil { totalAlbums >= 20 || currentPage == (page + 5) }
        .collectInto(mutableListOf(), { collected, next ->
          (collected as MutableList).addAll(next)
    })
  }
}