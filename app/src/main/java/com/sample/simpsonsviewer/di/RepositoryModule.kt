package com.sample.simpsonsviewer.di

import com.sample.simpsonsviewer.rest.SimpsonsRepository
import com.sample.simpsonsviewer.rest.SimpsonsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providesSimpsonsRepositoryImpl(simpsonsRepositoryImpl: SimpsonsRepositoryImpl):
            SimpsonsRepository
}