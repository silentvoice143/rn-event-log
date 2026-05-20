package com.rneventlog.core.utils

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap

object ReactMapConverter {

  fun readableToMap(readableMap: ReadableMap?): Map<String, Any?> {
    if (readableMap == null) return emptyMap()
    return readableMap.toHashMap()
  }

  fun mapToReadable(map: Map<String, Any?>?): WritableMap {
    val writableMap = Arguments.createMap()
    if (map == null) return writableMap

    for ((key, value) in map) {
      when (value) {
        is String -> writableMap.putString(key, value)
        is Int -> writableMap.putInt(key, value)
        is Double -> writableMap.putDouble(key, value)
        is Boolean -> writableMap.putBoolean(key, value)
        else -> writableMap.putString(key, value?.toString())
      }
    }

    return writableMap
  }
}