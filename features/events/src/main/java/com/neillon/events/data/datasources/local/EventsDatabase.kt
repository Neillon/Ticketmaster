package com.neillon.events.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [EventsEntity::class],
    version = 1
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class EventsDatabase: RoomDatabase() {
    abstract fun eventsDao(): EventsDao
}