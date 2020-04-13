package com.example.cleanfloyd

import com.example.cleanfloyd.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree

class CleanFloydApplication : DaggerApplication() {

  override fun onCreate() {
    super.onCreate()
    initializeTimber()
  }

  private val applicationInjector = DaggerApplicationComponent.builder().application(this).build()

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationInjector

  private fun initializeTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(DebugTree())
    }
  }
}