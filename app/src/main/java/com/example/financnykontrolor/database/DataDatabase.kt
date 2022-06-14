package com.example.financnykontrolor.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * abstract class which is used to using SQLite (database)
 *
 */
@Database(entities = [Data::class], version = 1, exportSchema =  false)
abstract class DataDatabase : RoomDatabase() {

    abstract val dataDatabaseDao: DataDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: DataDatabase? = null

        /**
         * function getInstance
         *
         * @param context for which content to execute
         * @return an instance of DataDatabase
         */
        fun getInstance(context: Context): DataDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, DataDatabase::class.java, "data_history_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}