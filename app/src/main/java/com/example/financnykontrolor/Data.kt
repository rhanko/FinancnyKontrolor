package com.example.financnykontrolor

import java.time.LocalDateTime

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
class Data(
    idl: Int,
    typel: Boolean = false,
    categoryl: Category = Category.OTHER,
    amountl: Int = 0,
    datel: LocalDateTime = LocalDateTime.now(),
    descriptionl: String = "1")
    {
    val id = idl
    var type = typel
    var category = categoryl
    var amount = amountl
    var date = datel
    var description = descriptionl

    /**
    *   function getDate return string of date in format [DD.MM.YYYY hh:mm:ss]
    */
    fun getDate(): String
    {
        return ( "" + date.dayOfMonth +
                "." + date.monthValue +
                "." + date.year +
                " " + date.hour +
                ":" + date.minute +
                ":" + date.second)
    }

    /**
     *  function getData return formatted string of data
     */
    fun getData(): String
    {
        val typeName: String = if (type) { "income" } else { "expense" }
        if (description != "")
        {
            return ("id: $id\n\t$typeName - $category\n\tamount: $amount\n\tdate: ${getDate()}\n\tdescription: $description")
        } else
        {
            return ("id: $id\n\t$typeName - $category\n\tamount: $amount\n\tdate: ${getDate()}")
        }
    }
}