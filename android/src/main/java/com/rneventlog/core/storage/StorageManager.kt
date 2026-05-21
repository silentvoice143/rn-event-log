package com.rneventlog.core.storage

import android.content.Context
import com.google.gson.Gson
import com.rneventlog.core.queue.Event
import com.rneventlog.core.debug.DebugEmitter

object StorageManager {

  private lateinit var db:
    AnalyticsDatabase

  private val gson =
    Gson()

  fun initialize(
    context: Context
  ) {

    db =
      AnalyticsDatabase
        .getInstance(context)
  }

  suspend fun save(
    event: Event
  ) {

    DebugEmitter.emit(
      "DB SAVE => ${event.event}"
    )

    db.eventDao().insert(

      EventEntity(
        event = event.event,
        properties = gson.toJson(
          event.properties
        ),
        timestamp =
          System.currentTimeMillis()
      )
    )
  }

  suspend fun getBatch(
  limit: Int
  ): List<EventEntity> {

  return db
    .eventDao()
    .getBatch(limit)
  }

  suspend fun getAll():
    List<EventEntity> {

    return db
      .eventDao()
      .getAll()
  }

  suspend fun delete(
    ids: List<Long>
  ) {

    db.eventDao()
      .deleteByIds(ids)
  }
}