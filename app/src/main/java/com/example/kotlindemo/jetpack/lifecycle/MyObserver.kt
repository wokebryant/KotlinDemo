package com.example.kotlindemo.jetpack.lifecycle

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class MyObserver(val lifecycle: Lifecycle) : LifecycleObserver {

    companion object {
        const val TAG = "MyObserver"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun activityStart() {
        Log.d(TAG, "activityStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun activityStop() {
        Log.d(TAG, "activityStop")

    }

    fun getCurrentLifeState(): Lifecycle.State {
        when(lifecycle.currentState) {
            Lifecycle.State.INITIALIZED -> activityStart()
            Lifecycle.State.DESTROYED -> activityStop()
        }
        return lifecycle.currentState
    }

}