package com.linversion.speedy

import android.app.Application
import com.linversion.speedy.data.DataModuleInitDelegate

/**
 * @author linversion
 * on 2023/12/17
 */
class SpeedyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        DataModuleInitDelegate.init(this)
    }
}