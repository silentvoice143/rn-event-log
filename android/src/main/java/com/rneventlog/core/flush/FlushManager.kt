package com.rneventlog.core.flush

import android.os.Handler
import android.os.Looper

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import com.rneventlog.core.debug.DebugEmitter
import com.rneventlog.core.storage.StorageManager
import com.rneventlog.core.transport.Transport

object FlushManager {

  private var flushAt = 20

  private var flushInterval =
    30000L

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

    handler.postDelayed(
      flushRunnable,
      flushInterval
    )
  }

  fun stop() {

    handler.removeCallbacks(
      flushRunnable
    )
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

    CoroutineScope(
      Dispatchers.IO
    ).launch {

      val batch =
        StorageManager.getBatch(20)

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

      if (result.success) {

        StorageManager.delete(

          batch.map { it.id }
        )

        DebugEmitter.emit(
          "Flush Success"
        )

      } else {

        DebugEmitter.emit(
          "Flush Failed"
        )
      }
    }
  }
}