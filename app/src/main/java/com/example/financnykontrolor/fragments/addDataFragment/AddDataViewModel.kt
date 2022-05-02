package com.example.financnykontrolor.fragments.addDataFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.financnykontrolor.database.Data
import com.example.financnykontrolor.database.DataDatabaseDao
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class AddDataViewModel(
    val database: DataDatabaseDao,
    application: Application) : AndroidViewModel(application)
{

    private var _id = MutableLiveData<Long>(-1)
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

    fun setID(id : Long) {
        _id.value = id
    }

    fun setAmount(amount : Double?) {
        if (amount != null && amount >= 0) {
            _amount.value = amount!!
        }
    }

    fun setType(type: Boolean) {
        _type.value = type
    }

    fun setCategory(category: String?) {
        _category.value = category
    }

    fun setDate(dateString: String?) {
        if (dateString != null) {
            _date.value = getMilliseconds(dateString)
        }
    }

    private fun getMilliseconds(dateString: String?): Long? {
        if (dateString != null) {
            val format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            val date = LocalDateTime.parse(dateString, format)
            return date.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()
        }
        return null
    }

    fun setDescription(description: String?) {
        _description.value = description

    }

    fun saveData() : Boolean {
        if (_amount.value != null && _amount.value!! >= 0) {
            viewModelScope.launch {
                val data = Data(
                    type = typeA.value,
                    category = categoryA.value,
                    amount = amountA.value!!,
                    date = dateA.value,
                    description = descriptionA.value
                )
                if (idA.value!! >= 0) {
                    database.insert(data)
                } else {
                    data.dataId = idA.value!!
                    database.update(data)
                }
            }
            return true
        } else {
            return false
        }

    }

    fun reinitializeData() {
        _amount = MutableLiveData<Double>()
        _type = MutableLiveData<Boolean?>()
        _category = MutableLiveData<String?>()
        _date = MutableLiveData<Long?>()
        _description = MutableLiveData<String?>()
    }
}