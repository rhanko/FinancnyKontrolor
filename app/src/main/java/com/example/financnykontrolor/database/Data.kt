package com.example.financnykontrolor.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * class Data is for working with data about money
 *
 * id = it's id of income or expense
 * type = if it's true then it's income, false then it's expense
 * category = where money goes or money goes from
 * amount = the amount of money
 * date = when money was used
 * description = a note of use
 */
@Entity(tableName = "data_about_money_table")
data class Data(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name ="data_id")
    var dataId: Long = 0L,

    var type: Boolean = false,

    var category: Category = Category.OTHER,

    var amount: Int = 0,

    var date: Long = System.currentTimeMillis(),

    var description: String = ""
)