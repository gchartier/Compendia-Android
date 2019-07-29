package com.gabrieldchartier.compendia.persistence

import android.util.Log
import androidx.room.TypeConverter
import com.gabrieldchartier.compendia.models.ComicCreator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.StringBuilder
import java.util.*
import java.util.Collections.emptyList



class Converters
{
    companion object
    {
        val TAG = "Converters"

        @TypeConverter
        @JvmStatic
        fun fromUUID(id: UUID?): String?
        {
            return id.toString()
        }

        @TypeConverter
        @JvmStatic
        fun toUUID(id: String?): UUID?
        {
            var returnID : UUID? = null

            //Log.d(TAG, "id == $id")

            if(!id.equals("null"))
                returnID = UUID.fromString(id)

            return returnID
        }

        @TypeConverter
        @JvmStatic
        fun fromUUIDList(ids: List<UUID>?): String?
        {
            val builder = StringBuilder()
            ids?.forEach {
                builder.append(it).append(",")
            }
            return builder.toString()
        }

        @TypeConverter
        @JvmStatic
        fun toUUIDList(ids: String?): List<UUID>?
        {
            val stringIDs : List<String>? = ids?.split(",")
            val retrievedIDs : ArrayList<UUID> = ArrayList()
            stringIDs?.forEach {
                if(it != "null" && !it.isNullOrEmpty())
                    retrievedIDs.add(UUID.fromString(it))
            }

            return retrievedIDs
        }

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
        fun stringToComicCreatorList(data: String?): List<ComicCreator> {
            val gson = Gson()
            if (data == null)
            {
                return emptyList()
            }

            val listType = object : TypeToken<List<ComicCreator>>() { }.type

            return gson.fromJson(data, listType)
        }

        @TypeConverter
        @JvmStatic
        fun comicCreatorsToString(comicCreators: List<ComicCreator>): String {
            return Gson().toJson(comicCreators)
        }
    }
}