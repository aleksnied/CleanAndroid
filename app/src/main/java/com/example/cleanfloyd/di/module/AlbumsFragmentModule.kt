package com.example.cleanfloyd.di.module

import com.example.cleanfloyd.presentation.albums.AlbumsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AlbumsFragmentModule {
  @ContributesAndroidInjector
  abstract fun provideAlbumsFragment(): AlbumsFragment
}