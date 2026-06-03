package com.rneventlog.core.navigation

import com.rneventlog.core.AnalyticsCore
import com.rneventlog.core.queue.Event

object ScreenTracker {

  private var currentScreen:
    String? = null

  private var startTime:
    Long = 0

  fun track(
    screen: String
  ) {

    val now =
      System.currentTimeMillis()

    currentScreen?.let {

      val duration =
        now - startTime

      AnalyticsCore.trackInternal(

        Event(
          event = "__screen_time__",

          properties = mapOf(

            "screen" to it,

            "startTime" to startTime,

            "endTime" to now,

            "duration" to duration
          )
        )
      )
    }

    currentScreen = screen

    startTime = now
  }
}