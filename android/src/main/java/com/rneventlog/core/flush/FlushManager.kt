package com.rneventlog.core.flush

import android.os.Handler
import android.os.Looper

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import com.rneventlog.core.debug.DebugEmitter
import com.rneventlog.core.network.NetworkManager
import com.rneventlog.core.storage.StorageManager
import com.rneventlog.core.transport.Transport

object FlushManager {

  private var isFlushing = false

  private var retryCount = 0

  private var nextRetryTime = 0L

  private var flushAt = 20

  private var flushInterval =
    30000L

  private var isStarted = false

  private val handler =
    Handler(
      Looper.getMainLooper()
    )

  private val flushRunnable =
    object : Runnable {

      override fun run() {

        flush()

        handler.postDelayed(
          this,
          flushInterval
        )
      }
    }

  fun configure(
    flushAtValue: Int?,
    flushIntervalValue: Double?
  ) {

    flushAt =
      flushAtValue ?: 20

    flushInterval =
      flushIntervalValue?.toLong()
        ?: 30000L
  }

  fun start() {

    if (isStarted) {
      return
    }

    isStarted = true

    handler.postDelayed(
      flushRunnable,
      flushInterval
    )
  }

  fun stop() {

    handler.removeCallbacks(
      flushRunnable
    )

    isStarted = false
  }

  fun checkAutoFlush(
    queueSize: Int
  ) {

    if (queueSize >= flushAt) {

      DebugEmitter.emit(
        "Auto Flush Triggered"
      )

      flush()
    }
  }

  fun flush() {

    if (isFlushing) {

      DebugEmitter.emit(
        "Flush Skipped => Already Running"
      )

      return
    }

    if (!NetworkManager.isConnected()) {

      DebugEmitter.emit(
        "Flush Skipped => Offline"
      )

      return
    }

    val now =
      System.currentTimeMillis()

    if (now < nextRetryTime) {

      DebugEmitter.emit(
        "Flush Delayed => Retry Backoff"
      )

      return
    }

    isFlushing = true

    CoroutineScope(
      Dispatchers.IO
    ).launch {

      try {

        val batch =
          StorageManager.getBatch(
            20
          )

        if (batch.isEmpty()) {

          DebugEmitter.emit(
            "Flush Skipped => Empty"
          )

          return@launch
        }

        DebugEmitter.emit(
          "Flush Batch => ${batch.size}"
        )

        val result =
          Transport.send(batch)

        if (result) {

          StorageManager.delete(

            batch.map { it.id }
          )

          retryCount = 0

          nextRetryTime = 0

          DebugEmitter.emit(
            "Flush Success"
          )

        } else {

          retryCount++

          val delay = minOf(

            30000L,

            retryCount * 5000L
          )

          nextRetryTime =

            System.currentTimeMillis() +
              delay

          DebugEmitter.emit(
            "Flush Failed"
          )

          DebugEmitter.emit(
            "Retry In => ${delay}ms"
          )
        }

      } finally {

        isFlushing = false
      }
    }
  }
}