package com.example.suki

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true) // (Optional) Whether to show thread info or not. Default true
            .methodCount(1) // (Optional) How many method line to show. Default 2
            .methodOffset(5)
            .tag("")
            .build()

        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))


        // Set methodOffset to 5 in order to hide internal method calls
        Timber.plant(object : Timber.DebugTree() {

            override fun log(
                priority: Int, tag: String?, message: String, t: Throwable?
            ) {
                Logger.log(priority, "x-$tag", message, t)
            }
        })

        // Usage - for JSON use Logger.json
        Timber.d("onCreate: Inside Application!")
    }
}

// Dependencies
// implementation 'com.orhanobut:logger:2.2.0'
// implementation 'com.jakewharton.timber:timber:4.7.1'
