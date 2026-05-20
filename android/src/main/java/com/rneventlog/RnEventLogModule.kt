package com.rneventlog

import android.util.Log

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.Arguments

import com.rneventlog.core.AnalyticsCore
import com.rneventlog.core.debug.DebugEmitter
import com.rneventlog.core.lifecycle.LifecycleTracker
import com.rneventlog.core.session.SessionManager
import com.rneventlog.core.utils.ReactMapConverter

class RnEventLogModule(
  reactContext: ReactApplicationContext
) : NativeRnEventLogSpec(reactContext) {

  //override fun addListener(eventName: String) {}
  //override fun removeListeners(count: Double) {}

  override fun init(config: ReadableMap?) {

    DebugEmitter.initialize(this)

      val strategy =
      config?.getString(
        "sessionStrategy"
      )

      val sessionTimeout =
      if (
        config?.hasKey(
          "sessionTimeout"
        ) == true
      ) {
        config.getDouble(
          "sessionTimeout"
        )
      } else {
        null
      }

    SessionManager.configure(
      strategy,
      sessionTimeout
    )

    LifecycleTracker.register()

    AnalyticsCore.init(config)
  }

  override fun track(
    event: String,
    properties: ReadableMap?
  ) {
    AnalyticsCore.track(
      event,
      properties
    )
  }

  override fun trackScreen(
    screen: String,
    properties: ReadableMap?
  ) {
    AnalyticsCore.trackScreen(
      screen,
      properties
    )
  }

  override fun identify(
    userId: String,
    traits: ReadableMap?
  ) {
    AnalyticsCore.identify(
      userId,
      traits
    )
  }

  override fun flush() {
    AnalyticsCore.flush()
  }

  override fun getSession(): WritableMap {

    return ReactMapConverter.mapToReadable(
      SessionManager.getSessionData()
    )
  }

  override fun startSession() {
    AnalyticsCore.startSession()
  }

  override fun closeSession() {
    AnalyticsCore.closeSession()
  }

  fun emitDebug(message: String) {

    emitOnDebug(
        Arguments.createMap().apply {
            putString("message", message)
        }
    )
  }

  companion object {
    const val NAME = NativeRnEventLogSpec.NAME
  }
}