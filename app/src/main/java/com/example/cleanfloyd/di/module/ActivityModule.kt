package com.example.cleanfloyd.di.module

import com.example.cleanfloyd.di.provider.AlbumsFragmentProvider
import com.example.cleanfloyd.presentation.albums.AlbumsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
interface ActivityModule {

  @ContributesAndroidInjector(modules = [AlbumsFragmentProvider::class])
  fun albumsActivityInjector(): AlbumsActivity
}