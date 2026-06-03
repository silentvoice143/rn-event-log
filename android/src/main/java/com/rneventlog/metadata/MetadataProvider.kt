package com.rneventlog.core.metadata

import android.os.Build

import com.rneventlog.BuildConfig
import com.rneventlog.core.session.SessionManager
import com.rneventlog.core.user.UserManager

object MetadataProvider {

  fun get():
    Map<String, Any?> {

    return mapOf(

      "userId" to UserManager.getUserId(),

      "userTraits" to UserManager.getTraits(),

      "platform" to "android",

      "sdkVersion" to "1.0.0",

      "osVersion" to
        Build.VERSION.RELEASE,

      "deviceModel" to
        Build.MODEL,

      "manufacturer" to
        Build.MANUFACTURER,

      "sessionId" to
        SessionManager.getSessionId()
    )
  }
}