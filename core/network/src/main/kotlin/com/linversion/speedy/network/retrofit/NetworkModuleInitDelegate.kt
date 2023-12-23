package com.linversion.speedy.network.retrofit

import android.app.Application
import io.github.rotbolt.flakerandroidokhttp.di.FlakerAndroidOkhttpContainer

/**
 * @author linversion
 * on 2023/12/17
 */
class NetworkModuleInitDelegate {
    companion object {
        fun init(application: Application) {
            FlakerAndroidOkhttpContainer.install(application)
        }
    }
}