package com.example.cleanfloyd.di.module

import com.example.cleanfloyd.presentation.albums.AlbumsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
interface ActivityModule {

  @ContributesAndroidInjector(modules = [AlbumsFragmentModule::class])
  fun albumsActivityInjector(): AlbumsActivity
}