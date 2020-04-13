package com.example.cleanfloyd.di.module

import android.app.Application
import android.content.Context
import com.example.cleanfloyd.schedulers.AsyncSchedulerProvider
import com.example.cleanfloyd.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelFactoryBuilder::class])
class ApplicationModule {

  @Singleton
  @Provides
  fun provideContext(application: Application): Context = application.applicationContext

  @Singleton
  @Provides
  fun provideScheduler(): SchedulerProvider = AsyncSchedulerProvider
}