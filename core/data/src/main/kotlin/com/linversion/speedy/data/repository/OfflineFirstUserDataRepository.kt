package com.linversion.speedy.data.repository

import com.linversion.speedy.core.datastore.NiaPreferencesDataSource
import com.linversion.speedy.model.DarkThemeConfig
import com.linversion.speedy.model.ThemeBrand
import com.linversion.speedy.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineFirstUserDataRepository @Inject constructor(
    private val niaPreferencesDataSource: NiaPreferencesDataSource
): UserDataRepository {
    override val userData: Flow<UserData> = niaPreferencesDataSource.userData

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        niaPreferencesDataSource.setThemeBrand(themeBrand)
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        niaPreferencesDataSource.setDarkThemeConfig(darkThemeConfig)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        niaPreferencesDataSource.setDynamicColorPreference(useDynamicColor)
//        analyticsHelper.logDynamicColorPreferenceChanged(useDynamicColor)
    }
}