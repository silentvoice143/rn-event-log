package com.rneventlog.core.transport

import com.rneventlog.core.debug.DebugEmitter
import com.rneventlog.core.queue.Event

object Transport {

  fun send(events: List<Event>) {

    DebugEmitter.emit(
      "Sending ${events.size} events"
    )

    events.forEach {

      DebugEmitter.emit(
        "Event => ${it.event}"
      )

      DebugEmitter.emit(
        "Properties => ${it.properties}"
      )
    }
  }
}