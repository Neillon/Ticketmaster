package com.neillon.events.data.datasources.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.LocalDate

@ProvidedTypeConverter
class LocalDateTimeConverter {

    @TypeConverter
    fun stringToDate(dateString: String?): LocalDate? {
        return if (dateString == null) {
            null
        } else {
            LocalDate.parse(dateString)
        }
    }

    @TypeConverter
    fun dateToString(date: LocalDate?): String? {
        return date?.toString()
    }
}