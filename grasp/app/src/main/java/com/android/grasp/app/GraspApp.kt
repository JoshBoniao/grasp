package com.android.grasp.app

import android.app.Application
import android.util.Log

class GraspApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("GraspApp", "onCreate is called")
    }
}
