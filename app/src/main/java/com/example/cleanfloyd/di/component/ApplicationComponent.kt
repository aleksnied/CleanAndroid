package com.example.cleanfloyd.di.component

import android.app.Application
import com.example.cleanfloyd.CleanFloydApplication
import com.example.cleanfloyd.di.module.ActivityModule
import com.example.cleanfloyd.di.module.ApplicationModule
import com.example.cleanfloyd.di.module.NetworkModule
import com.example.cleanfloyd.di.module.UseCaseModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
      AndroidSupportInjectionModule::class,
      ApplicationModule::class,
      ActivityModule::class,
      NetworkModule::class,
      UseCaseModule::class])
interface ApplicationComponent : AndroidInjector<CleanFloydApplication> {

  override fun inject(application: CleanFloydApplication)

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(application: Application): Builder

    fun build(): ApplicationComponent
  }
}