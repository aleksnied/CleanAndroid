package com.example.cleanfloyd.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object TestSchedulerProvider : SchedulerProvider {
  override val computation: Scheduler by lazy { Schedulers.trampoline() }

  override val io: Scheduler by lazy { Schedulers.trampoline() }

  override val ui: Scheduler by lazy { Schedulers.trampoline() }
}