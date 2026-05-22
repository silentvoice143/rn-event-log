package com.rneventlog.core.transport

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

import com.google.gson.Gson

import com.rneventlog.core.debug.DebugEmitter
import com.rneventlog.core.storage.EventEntity

object Transport {

  private val client =
    OkHttpClient()

  private val gson =
    Gson()

  fun send(
    events: List<EventEntity>
  ): Boolean {

    return try {

      if (
  TransportConfig.endpoint.isBlank()
) {

  DebugEmitter.emit(
    "Transport Skipped => No Endpoint"
  )

  return false
}

      val payload =
        mapOf(
          "events" to events
        )

      val json =
        gson.toJson(payload)

      DebugEmitter.emit(
        "Sending ${events.size} events"
      )

      val body =
        json.toRequestBody(
          "application/json"
            .toMediaType()
        )

      val requestBuilder =
        Request.Builder()
          .url(
            TransportConfig.endpoint
          )
          .post(body)

      TransportConfig.apiKey
        ?.let {

          requestBuilder.addHeader(
            "Authorization",
            it
          )
        }

      TransportConfig.headers
        .forEach {

          requestBuilder.addHeader(
            it.key,
            it.value
          )
        }

      val request =
        requestBuilder.build()

      val response =
        client.newCall(request)
          .execute()

      val success =
        response.isSuccessful

      if (success) {

        DebugEmitter.emit(
          "Transport Success"
        )

      } else {

        DebugEmitter.emit(
          "Transport Failed => ${response.code}"
        )
      }

      success

    } catch (e: Exception) {

      DebugEmitter.emit(
        "Transport Error => ${e.message}"
      )

      false
    }
  }
}