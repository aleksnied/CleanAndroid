package com.example.cleanfloyd.di.module

import androidx.lifecycle.ViewModel
import com.example.cleanfloyd.presentation.albums.AlbumsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
  @Binds
  @IntoMap
  @ViewModelKey(AlbumsViewModel::class)
  abstract fun bindAlbumsViewModel(albumsViewModel: AlbumsViewModel): ViewModel
}