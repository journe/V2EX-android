package com.journey.android.v2ex.libs

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * 应用生命周期
 */
class ProcessLifecycleObserver : DefaultLifecycleObserver {
  override fun onCreate(owner: LifecycleOwner) {
    //只会调用一次
    Log.i(TAG, "ProcessLifeonCreate: ")
//        LiveEventBus.get(Constants.PROCESS_LIFE_CREATE).post(true)
//        MyApplication.isFirst = true
  }

  override fun onResume(owner: LifecycleOwner) {
    Log.i(TAG, "ProcessLifeonResume: ")
  }

  override fun onPause(owner: LifecycleOwner) {
    Log.i(TAG, "ProcessLifeonPause: ")
//        MyApplication.isFirst = false
  }

  override fun onStart(owner: LifecycleOwner) {
    //进入前台
    Log.i(TAG, "ProcessLifeonStart: ")
  }

  override fun onStop(owner: LifecycleOwner) {
    // 进入后台
    Log.i(TAG, "ProcessLifeonStop: ")
  }

  override fun onDestroy(owner: LifecycleOwner) {
    // 不会调用
    Log.i(TAG, "ProcessLifeonDestroy: ")
  }

  companion object {
    private const val TAG = "ProcessLifecycle"
  }
}