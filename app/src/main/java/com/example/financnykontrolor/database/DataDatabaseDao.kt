package com.example.financnykontrolor.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * interface for database
 */
@Dao
interface DataDatabaseDao {

    @Insert
    suspend fun insert(data: Data)

    @Update
    suspend fun update(data: Data)

    @Query("DELETE FROM data_about_money_table WHERE data_id = :key")
    suspend fun delete(key: Long)

    @Query("SELECT * FROM data_about_money_table WHERE data_id = :key")
    suspend fun get(key: Long): Data?

    @Query("SELECT * FROM data_about_money_table WHERE date BETWEEN :date1 AND :date2 ORDER BY date desc")
    suspend fun get(date1: Long, date2: Long): Data?
}