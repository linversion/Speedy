package com.linversion.speedy.data.di

import com.linversion.speedy.data.repository.HomeRepository
import com.linversion.speedy.data.repository.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author linversion
 * on 2023/12/23
 */
@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository
}