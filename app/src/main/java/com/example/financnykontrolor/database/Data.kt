package com.example.financnykontrolor.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Class Data is for working with data about money
 *
 * @property dataId the ID of the data
 * @property type if it's true then it's income, false then it's expense
 * @property category where money goes or money goes from
 * @property amount the amount of money
 * @property date when money was used
 * @property description a note
 */
@Entity(tableName = "data_about_money_table")
data class Data(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name ="data_id")
    var dataId: Long = 0L,

    var type: Boolean?,

    var category: String?,

    var amount: Double?,

    var date: Long?,

    var description: String?
)