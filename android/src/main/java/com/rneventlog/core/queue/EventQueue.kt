package com.rneventlog.core.queue

object EventQueue {

  private val events = mutableListOf<Event>()

  fun add(event: Event) {
    events.add(event)
  }

  fun getAll(): List<Event> {
    return events.toList()
  }

  fun clear() {
    events.clear()
  }

  fun size(): Int {
    return events.size
  }
}