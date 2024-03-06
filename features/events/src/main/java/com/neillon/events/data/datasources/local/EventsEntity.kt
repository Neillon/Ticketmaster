package com.neillon.events.data.datasources.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "events"
)
data class EventsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("imageUrl") val imageUrl: String,
    @ColumnInfo("date") val date: LocalDate,
    @ColumnInfo("place") val place: String,
    @ColumnInfo("city") val city: String,
    @ColumnInfo("state") val state: String
)