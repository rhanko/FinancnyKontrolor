package com.example.financnykontrolor.fragments.addDataFragment

import android.app.Application
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.financnykontrolor.database.Data
import com.example.financnykontrolor.database.DataDatabaseDao
import com.example.financnykontrolor.getMilliseconds
import com.example.financnykontrolor.tryDateConvert
import kotlinx.coroutines.launch

/**
 * AddDataViewModel is ViewModel for Fragment AddData
 * with LiveData
 */
class AddDataViewModel(
    val database: DataDatabaseDao,
    application: Application,
    val fragment: AddDataFragment) : AndroidViewModel(application) {


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
     *  setter for id
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
     * Try dateTimeString if date and time are in right format return true
     * else return false
     */
    private fun trySetIntoDateTime(dateTimeString: String) : Boolean {
        if (dateTimeString.length == 19) {
            val dateString = dateTimeString.substring(0, 10)
            if (!tryDateConvert(dateString)) {
                return false
            }

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
            val data = database.getById(_id.value!!)
            _amount.value = data.amount!!
            _category.value = data.category
            _date.value = data.date
            _type.value = data.type
            _description.value = data.description
            fragment.reinitialization(false)
        }
    }
}