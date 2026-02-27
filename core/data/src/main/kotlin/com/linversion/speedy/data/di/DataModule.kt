package com.linversion.speedy.data.di

import com.linversion.speedy.data.repository.HomeRepository
import com.linversion.speedy.data.repository.HomeRepositoryImpl
import com.linversion.speedy.data.repository.OfflineFirstUserDataRepository
import com.linversion.speedy.data.repository.UserDataRepository
import com.linversion.speedy.data.util.ConnectivityManagerNetworkMonitor
import com.linversion.speedy.data.util.NetworkMonitor
import com.linversion.speedy.data.util.TimeZoneBroadcastMonitor
import com.linversion.speedy.data.util.TimeZoneMonitor
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
abstract class DataModule {
    @Binds
    internal abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    internal abstract fun bindsUserDataRepository(
        userDataRepository: OfflineFirstUserDataRepository,
    ): UserDataRepository

    @Binds
    internal abstract fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor

    @Binds
    internal abstract fun binds(impl: TimeZoneBroadcastMonitor): TimeZoneMonitor
}