package com.sample.simpsonsviewer.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.sample.simpsonsviewer.model.local.LocalRepository
import com.sample.simpsonsviewer.model.local.LocalRepositoryImpl
import com.sample.simpsonsviewer.model.local.SimpsonsDao
import com.sample.simpsonsviewer.model.local.SimpsonsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Provides
    fun providesConnectivityManager(
        @ApplicationContext context: Context
    ): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    @Singleton
    fun providesSimpsonsDao(
        @ApplicationContext context: Context
    ): SimpsonsDao = Room.databaseBuilder(
        context,
        SimpsonsDatabase::class.java,
        "simpsons-db"
    ).build().simpsonsDao

    @Provides
    fun providesLocalRepository(
        simpsonsDao: SimpsonsDao
    ): LocalRepository = LocalRepositoryImpl(simpsonsDao)

}