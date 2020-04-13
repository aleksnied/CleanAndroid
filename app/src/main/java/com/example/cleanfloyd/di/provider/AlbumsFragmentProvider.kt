package com.example.cleanfloyd.di.provider

import com.example.cleanfloyd.presentation.albums.AlbumsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AlbumsFragmentProvider {
  @ContributesAndroidInjector
  abstract fun provideAlbumsFragment(): AlbumsFragment
}