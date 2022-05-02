package com.example.financnykontrolor.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * interface for database
 */
@Dao
interface DataDatabaseDao {

    /**
     * fun for insert data to database
     */
    @Insert
    suspend fun insert(data: Data)

    /**
     * fun for update data in database
     */
    @Update
    suspend fun update(data: Data)


    /**
     * fun for delete data in database
     */
    @Query("DELETE FROM data_about_money_table WHERE data_id = :key")
    suspend fun delete(key: Long)


    /**
     * fun for get data from database
     * where
     */
    @Query("SELECT * FROM data_about_money_table WHERE data_id = :key")
    suspend fun get(key: Long): Data

    /**
     * fun for get data from database between 2 dates
     */
    @Query("SELECT * FROM data_about_money_table WHERE date BETWEEN :date1 AND :date2 ORDER BY date desc")
    suspend fun get(date1: Long, date2: Long): Data

    /**
     * fun for get all data from database
     */
    @Query("SELECT * FROM data_about_money_table ORDER BY date desc")
    fun getAllData(): LiveData<List<Data>>
}