package com.personal.klivvrsandroidchallenge.di

import android.content.Context
import com.personal.klivvrsandroidchallenge.data.repository.CityRepositoryImpl
import com.personal.klivvrsandroidchallenge.domain.repository.CityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCityRepository(@ApplicationContext context: Context): CityRepository {
        return CityRepositoryImpl(context)
    }
} 