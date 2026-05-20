package com.rneventlog.core.queue

data class Event(
  val event: String,
  val properties: Map<String, Any?> = emptyMap(),
  val timestamp: Long = System.currentTimeMillis()
)