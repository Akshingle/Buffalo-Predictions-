package com.buffalomilkpredictor

import android.app.Application
import timber.log.Timber

class BuffaloMilkPredictorApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Timber logging
        Timber.plant(Timber.DebugTree())
    }
}
