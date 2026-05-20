package com.rneventlog.core.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle

import com.rneventlog.core.AnalyticsCore
import com.rneventlog.core.debug.DebugEmitter
import com.rneventlog.core.session.SessionManager
import com.rneventlog.core.utils.ReactMapConverter

object LifecycleTracker :
  Application.ActivityLifecycleCallbacks {

  private var started = 0

  fun register(
  application: Application
) {

  DebugEmitter.emit(
    "LifecycleTracker Registered"
  )

  application
    .registerActivityLifecycleCallbacks(
      this
    )

  started = 1

  DebugEmitter.emit(
    "Initial Foreground Bootstrap"
  )

  val isNewSession =
    SessionManager.startOrResume()

  if (isNewSession) {

    DebugEmitter.emit(
      "Track => __app_open__"
    )

    AnalyticsCore.track(
      "__app_open__",
      null
    )

    DebugEmitter.emit(
      "Track => __session_start__"
    )

    AnalyticsCore.track(
      "__session_start__",
      ReactMapConverter.mapToReadable(
        SessionManager.getSessionData()
      )
    )
  }

  DebugEmitter.emit(
    "Track => __app_foreground__"
  )

  AnalyticsCore.track(
    "__app_foreground__",
    null
  )
}

  override fun onActivityStarted(
    activity: Activity
  ) {

    started++

    DebugEmitter.emit(
      "Activity Started => ${activity.localClassName}"
    )

    DebugEmitter.emit(
      "Started Count => $started"
    )

    if (started == 1) {

      DebugEmitter.emit(
        "App Entered Foreground"
      )

      val isNewSession =
        SessionManager.startOrResume()

      DebugEmitter.emit(
        "Is New Session => $isNewSession"
      )

      if (isNewSession) {

        DebugEmitter.emit(
          "Track => __session_start__"
        )

        DebugEmitter.emit(
          "Session => ${SessionManager.getSessionData()}"
        )

        AnalyticsCore.track(
          "__session_start__",
          ReactMapConverter.mapToReadable(
            SessionManager.getSessionData()
          )
        )
      }

      DebugEmitter.emit(
        "Track => __app_foreground__"
      )

      AnalyticsCore.track(
        "__app_foreground__",
        null
      )
    }
  }

  override fun onActivityStopped(
    activity: Activity
  ) {

    started--

    DebugEmitter.emit(
      "Activity Stopped => ${activity.localClassName}"
    )

    DebugEmitter.emit(
      "Started Count => $started"
    )

    if (started == 0) {

      DebugEmitter.emit(
        "App Entered Background"
      )

      SessionManager.onBackground()

      DebugEmitter.emit(
        "Track => __app_background__"
      )

      DebugEmitter.emit(
        "Session => ${SessionManager.getSessionData()}"
      )

      AnalyticsCore.track(
        "__app_background__",
        ReactMapConverter.mapToReadable(
          SessionManager.getSessionData()
        )
      )

      DebugEmitter.emit(
        "Flush Triggered"
      )

      AnalyticsCore.flush()
    }
  }

  override fun onActivityCreated(
    activity: Activity,
    savedInstanceState: Bundle?
  ) {}

  override fun onActivityResumed(
    activity: Activity
  ) {}

  override fun onActivityPaused(
    activity: Activity
  ) {}

  override fun onActivitySaveInstanceState(
    activity: Activity,
    outState: Bundle
  ) {}

  override fun onActivityDestroyed(
    activity: Activity
  ) {}
}