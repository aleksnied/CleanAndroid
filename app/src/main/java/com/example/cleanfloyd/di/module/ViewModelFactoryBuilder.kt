package com.example.cleanfloyd.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.cleanfloyd.presentation.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module(includes = [(ViewModelModule::class)])
abstract class ViewModelFactoryBuilder {
  @Binds
  abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}