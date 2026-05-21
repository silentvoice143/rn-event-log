package com.rneventlog.core.lifecycle

import android.os.Handler
import android.os.Looper

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

import com.rneventlog.core.AnalyticsCore
import com.rneventlog.core.debug.DebugEmitter
import com.rneventlog.core.session.SessionManager
import com.rneventlog.core.utils.ReactMapConverter

object LifecycleTracker :
  DefaultLifecycleObserver {

  private var isRegistered = false

  fun register() {

    if (isRegistered) {
      return
    }

    isRegistered = true

    Handler(
      Looper.getMainLooper()
    ).post {

      DebugEmitter.emit(
        "LifecycleTracker Registered"
      )

      ProcessLifecycleOwner
        .get()
        .lifecycle
        .addObserver(this)
    }
  }

  override fun onStart(
    owner: LifecycleOwner
  ) {

    DebugEmitter.emit(
      "Process Foreground"
    )

    val isNewSession =
      SessionManager.startOrResume()

    DebugEmitter.emit(
      "Is New Session => $isNewSession"
    )

    if (isNewSession) {

      AnalyticsCore.track(
        "__app_open__",
        null
      )

      AnalyticsCore.track(
        "__session_start__",
        ReactMapConverter.mapToReadable(
          SessionManager.getSessionData()
        )
      )
    }

    AnalyticsCore.track(
      "__app_foreground__",
      null
    )
  }

  override fun onStop(
  owner: LifecycleOwner
) {

  DebugEmitter.emit(
    "Process Background"
  )

  AnalyticsCore.track(
    "__app_background__",
    ReactMapConverter.mapToReadable(
      SessionManager.getSessionData()
    )
  )

  if (
    SessionManager
      .shouldCloseSessionOnBackground()
  ) {

    AnalyticsCore.closeSession()
  }

  SessionManager.onBackground()

  AnalyticsCore.flush()
}
}