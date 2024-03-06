package com.neillon.events.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EventsDao {
    @Query("SELECT * from events")
    suspend fun getAll(): List<EventsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg eventsEntity: EventsEntity)

    @Query("SELECT * FROM events WHERE name like '%' || :name || '%'")
    suspend fun searchByName(name: String): List<EventsEntity>
}