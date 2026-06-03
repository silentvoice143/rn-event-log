package com.rneventlog.core.worker

import android.content.Context

import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

import com.rneventlog.core.flush.FlushManager
import com.rneventlog.core.debug.DebugEmitter


class FlushWorker(
  context: Context,
  params: WorkerParameters
) : CoroutineWorker(
  context,
  params
) {

  override suspend fun doWork():
    Result {

    return try {

      FlushManager.flush()

      DebugEmitter.emit(
        "Background Worker Running"
      )

      Result.success()

    } catch (e: Exception) {

      Result.retry()
    }
  }
}