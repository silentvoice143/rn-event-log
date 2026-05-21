package com.rneventlog.core.session

import java.util.UUID

object SessionManager {

  private var sessionId: String? = null

  private var startTime: Long = 0

  private var lastBackgroundTime: Long = 0

  private var sessionTimeout = 30000L

  private var strategy =
    SessionStrategy.TIMEOUT

  fun configure(
    sessionStrategy: String?,
    timeout: Double?
  ) {

    strategy =
      sessionStrategy
        ?: SessionStrategy.TIMEOUT

    sessionTimeout =
      timeout?.toLong()
        ?: 30000L
  }

  fun startOrResume(): Boolean {

    val now =
      System.currentTimeMillis()

    val shouldCreateNewSession =
      if (
        strategy ==
        SessionStrategy.APP_STATE
      ) {

        sessionId == null ||
        lastBackgroundTime > 0

      } else {

        sessionId == null ||

        (now - lastBackgroundTime) >
        sessionTimeout
      }

    if (shouldCreateNewSession) {

      sessionId =
        UUID.randomUUID()
          .toString()

      startTime = now

      lastBackgroundTime = 0

      return true
    }

    return false
  }

  fun shouldCloseSessionOnBackground(): Boolean {
    return strategy == SessionStrategy.APP_STATE
  }

  fun onBackground() {

    lastBackgroundTime =
      System.currentTimeMillis()
  }

  fun getSessionData():
    Map<String, Any?> {

    val now =
      System.currentTimeMillis()

    return mapOf(
      "sessionId" to sessionId,
      "startTime" to startTime,
      "currentTime" to now,
      "duration" to (
        now - startTime
      )
    )
  }

  fun getSessionId():
    String? {

    return sessionId
  }

  fun clear() {

    sessionId = null

    startTime = 0

    lastBackgroundTime = 0
  }
}