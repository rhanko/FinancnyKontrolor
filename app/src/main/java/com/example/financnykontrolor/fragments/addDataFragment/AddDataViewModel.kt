package com.example.financnykontrolor.fragments.addDataFragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.financnykontrolor.database.Data
import com.example.financnykontrolor.database.DataDatabaseDao
import kotlinx.coroutines.launch

class AddDataViewModel(
    val database: DataDatabaseDao,
    application: Application) : AndroidViewModel(application)
{

    private var _amount = MutableLiveData<Double?>()
    val amountA : LiveData<Double?>
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


    fun setAmount(amount : Double?) {
        _amount.value = amount
    }

    fun setType(type: Boolean?) {
        _type.value = type
    }

    fun setCategory(category: String?) {

        _category.value = category
    }

    fun setDate(date: Long?) {
        _date.value = date
    }

    fun setDescription(description: String?) {
        _description.value = description

    }

    fun saveData() {
        Log.i("yeah we are he", "yeah yeah :)")
        viewModelScope.launch {
            val data = Data(
                type = typeA.value,
                category = categoryA.value,
                amount = amountA.value,
                date = dateA.value,
                description = descriptionA.value)
            Log.i("asnsda", "saonoianso")
            database.insert(data)
        }
        Log.i("insertData", "it is in insertData")

    }

    fun reinitializeData() {
        //_data = MutableLiveData<Data>()
    }
}