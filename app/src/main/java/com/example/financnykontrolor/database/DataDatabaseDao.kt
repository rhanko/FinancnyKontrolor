package com.example.financnykontrolor.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * interface for database
 *
 */
@Dao
interface DataDatabaseDao {

    /**
     * fun for insert data to database
     *
     * @param data which data to insert
     */
    @Insert
    suspend fun insert(data: Data)

    /**
     * fun for update data in database
     *
     * @param data which data to update
     */
    @Update
    suspend fun update(data: Data)

    /**
     * fun for delete data in database
     *
     * @param key the id of the data
     */
    @Query("DELETE FROM data_about_money_table WHERE data_id = :key")
    suspend fun delete(key: Long)

    /**
     * fun for get data from database
     *
     * @param key the id of the data
     * @return data from database
     */
    @Query("SELECT * FROM data_about_money_table WHERE data_id = :key")
    suspend fun getById(key: Long): Data

    /**
     * fun for get data from database
     *
     * @param date1 the date from
     * @param date2 the date until
     * @return list of all data between the [date1] and [date2]
     */
    @Query("SELECT * FROM data_about_money_table WHERE date BETWEEN :date1 AND :date2 ORDER BY date desc")
    suspend fun get(date1: Long, date2: Long): List<Data>

    /**
     * fun for get data from database
     *
     * @param date the date from
     * @return list of all data from the [date]
     */
    @Query("SELECT * FROM data_about_money_table WHERE date >= :date ORDER BY date desc")
    suspend fun get(date: Long): List<Data>

    /**
     * fun for get data from database
     *
     * @return list of all data
     */
    @Query("SELECT * FROM data_about_money_table ORDER BY date desc")
    suspend fun get(): List<Data>

    /**
     * fun for get all data from database
     *
     * @return all data in LiveData<List<Data>>
     */
    @Query("SELECT * FROM data_about_money_table ORDER BY date desc")
    fun getAllData(): LiveData<List<Data>>
}