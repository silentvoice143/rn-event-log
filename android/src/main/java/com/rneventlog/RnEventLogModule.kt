package com.rneventlog

import com.facebook.react.bridge.ReactApplicationContext

class RnEventLogModule(reactContext: ReactApplicationContext) :
  NativeRnEventLogSpec(reactContext) {

  override fun multiply(a: Double, b: Double): Double {
    return a * b
  }

  companion object {
    const val NAME = NativeRnEventLogSpec.NAME
  }
}
