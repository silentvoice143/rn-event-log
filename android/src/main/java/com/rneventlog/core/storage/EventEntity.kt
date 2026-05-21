package com.rneventlog.core.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
  tableName = "events"
)
data class EventEntity(

  @PrimaryKey(
    autoGenerate = true
  )
  val id: Long = 0,

  val event: String,

  val properties: String?,

  val timestamp: Long
)