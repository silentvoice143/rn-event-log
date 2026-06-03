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
import com.rneventlog.core.metadata.MetadataProvider
import com.rneventlog.core.config.GlobalProperties
import com.rneventlog.core.user.UserManager

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

 

  val eventProps =
  ReactMapConverter.readableToMap(
    properties
  )

val props =
  MetadataProvider
    .get()
    .toMutableMap()

props.putAll(
  GlobalProperties.get()
)

props.putAll(
  eventProps
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

  val mappedTraits =

    ReactMapConverter
      .readableToMap(
        traits
      )

  UserManager.identify(
    userId,
    mappedTraits
  )

  DebugEmitter.emit(
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