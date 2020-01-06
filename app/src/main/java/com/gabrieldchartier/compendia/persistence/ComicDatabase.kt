package com.gabrieldchartier.compendia.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gabrieldchartier.compendia.models.Comic

//@TypeConverters(Converters::class)
//@Database(entities = [Comic::class], version = 3)
//abstract class ComicDatabase : RoomDatabase()
//{
//
//    companion object
//    {
//        val DATABASE_NAME = "comic_db"
//
//        private var instance: ComicDatabase? = null
//
//        internal fun getInstance(context: Context): ComicDatabase {
//            if (instance == null) {
//                instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        ComicDatabase::class.java,
//                        DATABASE_NAME
//                ).fallbackToDestructiveMigration().build()
//            }
//            return instance as ComicDatabase
//        }
//    }
//
//    abstract fun getNewReleaseDao() : NewReleaseDao
//}
