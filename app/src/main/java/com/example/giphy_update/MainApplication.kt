package com.example.giphy_update

import android.app.Application
import com.facebook.stetho.BuildConfig
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import timber.log.Timber


@HiltAndroidApp // hilt 의존성이 앱 라이프사이클에 묶인다.
class MainApplication : Application()
   // , KoinComponent
{

    override fun onCreate() {
        super.onCreate()
//        startKoin {
//            initKoinApplication(this@MainApplication)
//        }

       // if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        //}

        Stetho.initializeWithDefaults(this)
    }
}

//fun KoinApplication.initKoinApplication(application: Application) {
//    androidContext(application)
//    androidLogger()
//    koin.loadModules(applicationModule)
//    koin.createRootScope()
//}