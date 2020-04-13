package com.example.cleanfloyd.di.module

import com.example.cleanfloyd.data.repository.AlbumsRepository
import com.example.cleanfloyd.domain.usecase.GetAlbumsUseCase
import com.example.cleanfloyd.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ApplicationModule::class, NetworkModule::class])
class UseCaseModule {
    @Provides
    @Singleton
    fun providesGetAlbumsUseCase(
        repository: AlbumsRepository,
        schedulerProvider: SchedulerProvider
    ): GetAlbumsUseCase = GetAlbumsUseCase(repository, schedulerProvider)
}