package com.example.financnykontrolor.fragments.addDataFragment

import android.app.Application
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.financnykontrolor.database.Data
import com.example.financnykontrolor.database.DataDatabaseDao
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * AddDataViewModel is ViewModel for Fragment AddData
 * with LiveData
 */
class AddDataViewModel(
    val database: DataDatabaseDao,
    application: Application) : AndroidViewModel(application) {
    private val format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

    private var _id = MutableLiveData<Long>(10)
    val idA : LiveData<Long>
        get() = _id

    private var _amount = MutableLiveData<Double>()
    val amountA : LiveData<Double>
        get() = _amount

    private var _type = MutableLiveData<Boolean?>()
    val typeA : LiveData<Boolean?>
        get() = _type

    private var _category = MutableLiveData<String?>()
    val categoryA : LiveData<String?>
        get() = _category

    private var _date = MutableLiveData<Long?>()
    val dateA : LiveData<Long?>
        get() = _date

    private var _description = MutableLiveData<String?>()
    val descriptionA : LiveData<String?>
        get() = _description

    /**
     *
     */
    fun setId(id: Long) {
        _id.value = id
    }

    /**
     * setter for amount
     */
    fun setAmount(amount : Double?) {
        if (amount != null && amount >= 0) {
            _amount.value = amount!!
        }
    }

    /**
     * setter for type
     */
    fun setType(type: Boolean) {
        _type.value = type
    }

    /**
     * setter for category
     */
    fun setCategory(category: String?) {
        _category.value = category
    }

    /**
     * setter for date
     */
    fun setDate(dateString: String?) {
        if (dateString != null) {
            if (trySetIntoDateTime(dateString)) {
                _date.value = getMilliseconds(dateString)
            }
        }
    }

    /**
     * Try dateTimeString if can be a date and time return true
     * else return false
     */
    private fun trySetIntoDateTime(dateTimeString: String) : Boolean {
        if (dateTimeString.length == 19) {
            val dateString = dateTimeString.substring(0, 10)

            //if there are "/"
            for (i in 2..5 step 3) {
                if (dateString[i].toString() != "/") {
                    return false
                }
            }

            //if month is correct
            val month = dateString.substring(3, 5)
            if (month.isDigitsOnly()) {
                if (month.toInt() !in (1..12)) {return false}
            } else {return false}

            //if year is correct
            val year = dateString.substring(6, 10)
            for (i in 1..4) {
                if (!year.isDigitsOnly()) {return false}
            }

            //if day is correct
            val day = dateString.substring(0, 2)
            if (day.isDigitsOnly() && day.toInt() <= 31 && day.toInt() >= 1) {
                when (day.toShort()) {
                    in (31..31) -> if (month.toInt() != (1 or 3 or 5 or 7 or 8 or 10 or 12) ) {return false}
                    in (30..30) -> if (month.toInt() == 2) {return false}
                    in (29..29) -> if (month.toInt() == 2 && year.toInt().mod(4) == 0) {return false}
                }
            } else {return false}



            //If we are there, date is correct ... so let's try a time
            val timeString = dateTimeString.substring(11)


            //if there are ":"
            for (i in 2..5 step 3) {
                if (timeString[i].toString() != ":") {return false}
            }

            val hour = timeString.substring(0,2)
            if (!hour.isDigitsOnly() && hour.toInt() < 24) {return false}

            val minute = timeString.substring(3,5)
            if (!minute.isDigitsOnly() && minute.toInt() < 60) {return false}

            val second = timeString.substring(6)
            if (!second.isDigitsOnly() && second.toInt() < 60) {return false}

            return true
        }
        return false
    }

    /**
     * Change date and time to milliseconds
     */
    private fun getMilliseconds(dateString: String?): Long? {
        if (dateString != null) {
            val date = LocalDateTime.parse(dateString, format)
            return date.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()
        }
        return null
    }

    /**
     * setter for description
     */
    fun setDescription(description: String?) {
        _description.value = description

    }

    /**
     * Save data to database
     * return true if it saved sucessfully
     */
    fun saveData() : Boolean {
        if (_amount.value != null && _amount.value!! >= 0 && _category.value != null && _date.value != null) {
            viewModelScope.launch {
                val data = Data(
                    type = typeA.value,
                    category = categoryA.value,
                    amount = amountA.value!!,
                    date = dateA.value,
                    description = descriptionA.value
                )
                if (idA.value!! > 0) {
                    data.dataId = idA.value!!
                    database.update(data)
                } else {
                    database.insert(data)
                }
            }
            return true
        } else {
            return false
        }

    }

    /**
     * for reinitialization data
     */
    fun reinitializeData() {
        _amount = MutableLiveData<Double>()
        _type = MutableLiveData<Boolean?>()
        _category = MutableLiveData<String?>()
        _date = MutableLiveData<Long?>()
        _description = MutableLiveData<String?>()
    }

    /**
     * initialize data from database
     */
    fun initializeDataFromDatabase() {
        viewModelScope.launch {
        val data = database.get(_id.value!!)
        _amount.value = data.amount!!
        _category.value = data.category
        _date.value = data.date
        _type.value = data.type
        _description.value = data.description
    }
    }
}