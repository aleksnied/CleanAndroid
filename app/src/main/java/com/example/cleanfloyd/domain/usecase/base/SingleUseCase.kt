package com.example.cleanfloyd.domain.usecase.base

import com.example.cleanfloyd.schedulers.SchedulerProvider
import io.reactivex.Single

typealias OnSuccess<T> = (T) -> Unit
typealias OnError = (Throwable) -> Unit
typealias OnFinished = () -> Unit

abstract class SingleUseCase<T>(scheduler: SchedulerProvider) : UseCase(scheduler) {

  internal abstract fun buildUseCaseSingle(): Single<T>

  fun execute(
    onOnSuccess: OnSuccess<T>, onOnError: OnError, onOnFinished: OnFinished = {}
  ) {
    disposeLast()
    lastDisposable =
        buildUseCaseSingle().subscribeOn(scheduler.io)
            .observeOn(scheduler.ui).doAfterTerminate(onOnFinished)
            .subscribe(onOnSuccess, onOnError)

    lastDisposable?.let {
      compositeDisposable.add(it)
    }
  }
}