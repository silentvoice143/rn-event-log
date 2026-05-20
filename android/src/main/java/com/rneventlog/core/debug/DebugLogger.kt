package com.rneventlog.core.debug

import android.util.Log

object DebugLogger {

  private const val TAG = "RnEventLog"

  fun log(message: String) {
    Log.d(TAG, message)
  }
}