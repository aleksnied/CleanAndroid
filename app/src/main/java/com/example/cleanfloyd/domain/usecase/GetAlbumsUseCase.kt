package com.example.cleanfloyd.domain.usecase

import com.example.cleanfloyd.data.repository.AlbumsRepository
import com.example.cleanfloyd.domain.model.Album
import com.example.cleanfloyd.domain.usecase.base.SingleUseCase
import com.example.cleanfloyd.schedulers.SchedulerProvider
import io.reactivex.Single
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(private val repository: AlbumsRepository, scheduler: SchedulerProvider) : SingleUseCase<List<Album>>(scheduler) {

  override fun buildUseCaseSingle(): Single<List<Album>> {
    return repository.getAlbums()
  }
}