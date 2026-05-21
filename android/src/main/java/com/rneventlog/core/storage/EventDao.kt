package com.rneventlog.core.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EventDao {

  @Insert
  suspend fun insert(
    event: EventEntity
  )

  @Query(
    "SELECT * FROM events ORDER BY id ASC"
  )
  suspend fun getAll():
    List<EventEntity>

  @Query(
    "DELETE FROM events WHERE id IN (:ids)"
  )
  suspend fun deleteByIds(
    ids: List<Long>
  )

  @Query(
    "DELETE FROM events"
  )
  suspend fun clear()

  @Query(
  "SELECT * FROM events ORDER BY id ASC LIMIT :limit"
  )
  suspend fun getBatch(
  limit: Int
  ): List<EventEntity>
}