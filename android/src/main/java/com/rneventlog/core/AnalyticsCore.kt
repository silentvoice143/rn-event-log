package com.rneventlog.core

import com.facebook.react.bridge.ReadableMap

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.rneventlog.core.debug.DebugLogger
import com.rneventlog.core.flush.FlushManager
import com.rneventlog.core.navigation.ScreenTracker
import com.rneventlog.core.queue.Event
import com.rneventlog.core.queue.EventQueue
import com.rneventlog.core.utils.ReactMapConverter
import com.rneventlog.core.debug.DebugEmitter
import com.rneventlog.core.storage.StorageManager

object AnalyticsCore {

  fun init(config: ReadableMap?) {

    DebugLogger.log(
      "SDK Initialized"
    )
  }

  fun trackInternal(
  trackedEvent: Event
) {

  DebugEmitter.emit(
    "Queue => ${trackedEvent.event}"
  )

  EventQueue.add(
    trackedEvent
  )

  CoroutineScope(
    Dispatchers.IO
  ).launch {

    StorageManager.save(
      trackedEvent
    )

    FlushManager.checkAutoFlush(
      EventQueue.size()
    )
  }
}

  fun track(
  event: String,
  properties: ReadableMap?
) {

 

  val props =
    ReactMapConverter.readableToMap(
      properties
    )

  trackInternal(

    Event(
      event = event,
      properties = props
    )
  )
}

  fun trackScreen(
    screen: String,
    properties: ReadableMap?
  ) {

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
    

    trackInternal(

  Event(
    event = "__session_start__"
  )
)
  }

  fun closeSession() {


    trackInternal(

  Event(
    event = "__session_end__"
  )
)
  }
}