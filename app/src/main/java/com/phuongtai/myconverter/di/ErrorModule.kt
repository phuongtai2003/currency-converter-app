package com.phuongtai.myconverter.di

import com.phuongtai.myconverter.data.error.mapper.ErrorMapper
import com.phuongtai.myconverter.data.error.mapper.ErrorMapperSource
import com.phuongtai.myconverter.errors.ErrorManager
import com.phuongtai.myconverter.errors.ErrorUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorModule {
    @Binds
    @Singleton
    abstract fun provideErrorFactoryImpl(errorManager: ErrorManager): ErrorUseCase

    @Binds
    @Singleton
    abstract fun provideErrorMapper(errorMapper: ErrorMapper): ErrorMapperSource
}