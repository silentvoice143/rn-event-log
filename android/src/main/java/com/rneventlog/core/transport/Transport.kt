package com.rneventlog.core.transport

import com.rneventlog.core.debug.DebugEmitter
import com.rneventlog.core.storage.EventEntity

object Transport {

  suspend fun send(
    events: List<EventEntity>
  ): TransportResult {

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

    return TransportResult(
      success = true
    )
  }
}