package com.example.cleanfloyd.domain.usecase

import com.example.cleanfloyd.data.repository.AlbumsRepository
import com.example.cleanfloyd.domain.model.Album
import com.example.cleanfloyd.domain.usecase.base.SingleUseCase
import com.example.cleanfloyd.schedulers.SchedulerProvider
import io.reactivex.Single
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(private val repository: AlbumsRepository, scheduler: SchedulerProvider) : SingleUseCase<Pair<List<Album>, Int>>(scheduler) {

  public var currentPage = 1

  override fun buildUseCaseSingle(): Single<Pair<List<Album>, Int>> {
    return repository.getAlbums(currentPage)
  }
}