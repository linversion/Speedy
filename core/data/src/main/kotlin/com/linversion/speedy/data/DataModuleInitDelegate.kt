package com.linversion.speedy.data

import android.app.Application
import com.linversion.speedy.network.retrofit.NetworkModuleInitDelegate

/**
 * @author linversion
 * on 2023/12/17
 */
class DataModuleInitDelegate {
    companion object {
        fun init(application: Application) {
            NetworkModuleInitDelegate.init(application)
        }
    }
}