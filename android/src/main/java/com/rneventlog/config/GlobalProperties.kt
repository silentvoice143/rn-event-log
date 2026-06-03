package com.rneventlog.core.config

object GlobalProperties {

  private val properties =
    mutableMapOf<String, Any?>()

  fun set(
    values: Map<String, Any?>
  ) {

    properties.putAll(values)
  }

  fun get():
    Map<String, Any?> {

    return properties
  }

  fun clear() {

    properties.clear()
  }
}