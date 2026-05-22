package com.rneventlog

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.Arguments

import com.rneventlog.core.AnalyticsCore
import com.rneventlog.core.debug.DebugEmitter
import com.rneventlog.core.lifecycle.LifecycleTracker
import com.rneventlog.core.session.SessionManager
import com.rneventlog.core.utils.ReactMapConverter
import com.rneventlog.core.storage.StorageManager
import com.rneventlog.core.flush.FlushManager
import com.rneventlog.core.transport.TransportConfig
import com.facebook.react.bridge.Promise
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.rneventlog.core.config.GlobalProperties
import com.rneventlog.core.debug.DebugConfig
import com.rneventlog.core.network.NetworkManager
import com.rneventlog.core.storage.StorageConfig
import com.rneventlog.core.user.UserManager
import com.rneventlog.core.worker.WorkerScheduler

class RnEventLogModule(
  reactContext: ReactApplicationContext
) : NativeRnEventLogSpec(reactContext) {

  //override fun addListener(eventName: String) {}
  //override fun removeListeners(count: Double) {}

  

  override fun init(config: ReadableMap?) {

    DebugEmitter.initialize(this)
  UserManager.initialize(
  reactApplicationContext
  )
  NetworkManager.initialize(
  reactApplicationContext)

  val maxStoredEvents =
  if (
    config?.hasKey(
      "maxStoredEvents"
    ) == true
  ) {

    config.getInt(
      "maxStoredEvents"
    )

  } else {
    null
  }


maxStoredEvents?.let {

  StorageConfig.maxStoredEvents =
    it
}
    StorageManager.initialize(
      reactApplicationContext
    )

    

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

      val flushAt =
  if (
    config?.hasKey(
      "flushAt"
    ) == true
  ) {

    config.getInt(
      "flushAt"
    )

  } else {
    null
  }

val flushInterval =
  if (
    config?.hasKey(
      "flushInterval"
    ) == true
  ) {

    config.getDouble(
      "flushInterval"
    )

  } else {
    null
  }

    SessionManager.configure(
      strategy,
      sessionTimeout
    )

    FlushManager.configure(
  flushAt,
  flushInterval
)

    FlushManager.start()
WorkerScheduler.start(
  reactApplicationContext
)
   

  val debug =
  if (
    config?.hasKey(
      "debug"
    ) == true
  ) {

    config.getBoolean(
      "debug"
    )

  } else {

    false
  }

DebugConfig.enabled =
  debug

    val endpoint =
  config?.getString(
    "endpoint"
  )

val apiKey =
  config?.getString(
    "apiKey"
  )

val headers =
  if (
    config?.hasKey(
      "headers"
    ) == true
  ) {

    ReactMapConverter
      .readableToMap(
        config.getMap(
          "headers"
        )
      )

  } else {
    emptyMap()
  }

if (endpoint != null) {
  TransportConfig.endpoint =
    endpoint
}

TransportConfig.apiKey =
  apiKey

  TransportConfig.headers =
  headers.mapValues {
    it.value.toString()
  }



    LifecycleTracker.register()

    AnalyticsCore.init(config)
  }

  override fun getStoredEvents(
  promise: Promise
) {

  CoroutineScope(
    Dispatchers.IO
  ).launch {

    try {

      val events =
        StorageManager.getAll()

      val array =
        Arguments.createArray()

      events.forEach {

        val map =
          Arguments.createMap()

        map.putDouble(
          "id",
          it.id.toDouble()
        )

        map.putString(
          "event",
          it.event
        )

        map.putString(
          "properties",
          it.properties
        )

        map.putDouble(
          "timestamp",
          it.timestamp.toDouble()
        )

        array.pushMap(map)
      }

      promise.resolve(array)

    } catch (e: Exception) {

      promise.reject(
        "GET_EVENTS_ERROR",
        e
      )
    }
  }
}

override fun setGlobalProperties(
  properties: ReadableMap
) {

  GlobalProperties.set(

    ReactMapConverter
      .readableToMap(
        properties
      )
  )
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