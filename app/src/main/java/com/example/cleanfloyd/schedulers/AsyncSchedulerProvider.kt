package com.example.cleanfloyd.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object AsyncSchedulerProvider : SchedulerProvider {
  override val computation: Scheduler by lazy { Schedulers.computation() }

  override val io: Scheduler by lazy { Schedulers.io() }

  override val ui: Scheduler by lazy { AndroidSchedulers.mainThread() }
}