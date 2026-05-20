package com.rneventlog.core

import com.facebook.react.bridge.ReadableMap

import com.rneventlog.core.debug.DebugLogger
import com.rneventlog.core.flush.FlushManager
import com.rneventlog.core.navigation.ScreenTracker
import com.rneventlog.core.queue.Event
import com.rneventlog.core.queue.EventQueue

object AnalyticsCore {

  fun init(config: ReadableMap?) {

    DebugLogger.log(
      "SDK Initialized"
    )
  }

  fun track(
    event: String,
    properties: ReadableMap?
  ) {

    DebugLogger.log(
    "Native Track => $event"
   )

    EventQueue.add(
      Event(
        event = event
      )
    )

    DebugLogger.log(
      "Queue Size => ${EventQueue.size()}"
    )
  }

  fun trackScreen(
    screen: String,
    properties: ReadableMap?
  ) {

    DebugLogger.log(
      "Screen => $screen"
    )

    ScreenTracker.track(screen)
  }

  fun flush() {

    DebugLogger.log(
      "Flush Called"
    )

    FlushManager.flush()
  }

  fun identify(
    userId: String,
    traits: ReadableMap?
  ) {
    DebugLogger.log(
      "Identify => $userId"
    )
  }

  fun startSession() {

    EventQueue.add(
      Event(
        event = "__session_start__"
      )
    )
  }

  fun closeSession() {

    EventQueue.add(
      Event(
        event = "__session_end__"
      )
    )
  }
}