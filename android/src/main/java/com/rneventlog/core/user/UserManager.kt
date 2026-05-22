package com.rneventlog.core.user

import android.content.Context

import com.google.gson.Gson

object UserManager {

  private const val PREFS =
    "rn_event_log_user"

  private const val USER_ID =
    "user_id"

  private const val TRAITS =
    "traits"

  private lateinit var context:
    Context

  private val gson =
    Gson()

  fun initialize(
    appContext: Context
  ) {

    context =
      appContext.applicationContext
  }

  fun identify(
    userId: String,
    traits: Map<String, Any?>
  ) {

    val prefs =
      context.getSharedPreferences(
        PREFS,
        Context.MODE_PRIVATE
      )

    prefs.edit()

      .putString(
        USER_ID,
        userId
      )

      .putString(
        TRAITS,
        gson.toJson(traits)
      )

      .apply()
  }

  fun getUserId():
    String? {

    val prefs =
      context.getSharedPreferences(
        PREFS,
        Context.MODE_PRIVATE
      )

    return prefs.getString(
      USER_ID,
      null
    )
  }

  fun getTraits():
    Map<String, Any?> {

    val prefs =
      context.getSharedPreferences(
        PREFS,
        Context.MODE_PRIVATE
      )

    val json =
      prefs.getString(
        TRAITS,
        null
      ) ?: return emptyMap()

    return gson.fromJson(
      json,
      Map::class.java
    ) as Map<String, Any?>
  }

  fun clear() {

    val prefs =
      context.getSharedPreferences(
        PREFS,
        Context.MODE_PRIVATE
      )

    prefs.edit().clear().apply()
  }
}