package com.rneventlog.core.worker

import android.content.Context

import androidx.work.*

import java.util.concurrent.TimeUnit

object WorkerScheduler {

  fun start(
    context: Context
  ) {

    val constraints =

      Constraints.Builder()

        .setRequiredNetworkType(
          NetworkType.CONNECTED
        )

        .build()

    val request =

      PeriodicWorkRequestBuilder<
        FlushWorker
      >(
        15,
        TimeUnit.MINUTES
      )

      .setConstraints(
        constraints
      )

      .build()

    WorkManager
      .getInstance(context)

      .enqueueUniquePeriodicWork(

        "rn_event_log_flush",

        ExistingPeriodicWorkPolicy.KEEP,

        request
      )
  }
}