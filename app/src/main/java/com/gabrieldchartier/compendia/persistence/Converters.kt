package com.gabrieldchartier.compendia.persistence

import androidx.room.TypeConverter
import java.lang.StringBuilder
import java.util.*

class Converters
{
    companion object
    {
        val TAG = "Converters"

        @TypeConverter
        @JvmStatic
        fun fromStringArray(strings: Array<String>?): String?
        {
            val builder = StringBuilder()
            strings?.forEach {
                builder.append(it).append(",")
            }
            return builder.toString()
        }

        @TypeConverter
        @JvmStatic
        fun toStringArray(strings: String?): Array<String>?
        {
            return strings?.split(",")?.toTypedArray()
        }

        @TypeConverter
        @JvmStatic
        fun fromTimestamp(value: Long?): Date? {
            return value?.let { Date(it) }
        }

        @TypeConverter
        @JvmStatic
        fun dateToTimestamp(date: Date?): Long? {
            return date?.time
        }
    }
}