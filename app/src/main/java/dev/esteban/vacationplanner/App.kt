package dev.esteban.vacationplanner

import android.app.Application
import dev.esteban.vacationplanner.BuildConfig.DEBUG
import dev.esteban.vacationplanner.di.app.injectFeatures
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            if (DEBUG) androidLogger()
            androidContext(this@App)
            injectFeatures()
        }
    }
}
