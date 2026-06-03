package com.rneventlog.core.storage

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
  entities = [
    EventEntity::class
  ],
  version = 1
)
abstract class AnalyticsDatabase :
  RoomDatabase() {

  abstract fun eventDao():
    EventDao

  companion object {

    @Volatile
    private var INSTANCE:
      AnalyticsDatabase? = null

    fun getInstance(
      context: Context
    ): AnalyticsDatabase {

      return INSTANCE
        ?: synchronized(this) {

          val instance =
            Room.databaseBuilder(
              context,
              AnalyticsDatabase::class.java,
              "rn_event_log_db"
            ).build()

          INSTANCE = instance

          instance
        }
    }
  }
}