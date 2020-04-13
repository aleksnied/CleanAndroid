package com.example.cleanfloyd.data.source.remote

import com.example.cleanfloyd.domain.model.Album
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumsService {
  @GET("artists/45467/releases")
  fun getAlbums(@Query("page") page: Int): Single<ReleasesResponse>
}

data class ReleasesResponse(val releases: List<Album>)