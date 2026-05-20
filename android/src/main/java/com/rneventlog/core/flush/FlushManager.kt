package com.rneventlog.core.flush

import com.rneventlog.core.queue.EventQueue
import com.rneventlog.core.transport.Transport

object FlushManager {

  fun flush() {

    val events = EventQueue.getAll()

    if (events.isEmpty()) {
      return
    }

    Transport.send(events)

    EventQueue.clear()
  }
}