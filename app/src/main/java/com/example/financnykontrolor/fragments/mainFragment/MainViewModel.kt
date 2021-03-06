package com.example.financnykontrolor.fragments.mainFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androidplot.pie.Segment
import com.example.financnykontrolor.database.Data
import com.example.financnykontrolor.database.DataDatabaseDao
import com.example.financnykontrolor.getMilliseconds
import com.example.financnykontrolor.tryDateConvert
import kotlinx.coroutines.launch
/**
 * MainViewModel is ViewModel for Fragment Main
 * with LiveData
 */
class MainViewModel(
    val database: DataDatabaseDao,
    application: Application,
    val fragment: MainFragment
) : AndroidViewModel(application) {

    private var _expense = MutableLiveData(0.0)
    val expense : LiveData<Double>
        get() = _expense

    private var _income = MutableLiveData(0.0)
    val income : LiveData<Double>
        get() = _income

    private var _segmentIncome = MutableLiveData<Segment>()
    val segmentIncome: LiveData<Segment>
        get() = _segmentIncome
    private var _segmentExpense = MutableLiveData<Segment>()
    val segmentExpense: LiveData<Segment>
        get() = _segmentExpense

    /**
     * set all data from database
     */
    fun setAllData() {
        viewModelScope.launch {
            val data = database.get()
            setAmount(data)
        }
    }

    /**
     * initialize segments of graph
     */
    private fun segmentsInit() {
        _segmentIncome.value = Segment("Príjem", _income.value)
        _segmentExpense.value = Segment("Výdavky", _expense.value)
        fragment.addDataToGraph()
    }

    /**
     * set data from database from date in String
     * if date is compatibles return true and data will be from this date
     * else return false and data do not change
     */
    fun setDataFrom(date : String) {
        val dateL = convertDateToLong(date)

        if (dateL >= 0) {
            viewModelScope.launch {
                val data = database.get(dateL)
                setAmount(data)
            }
        }
    }

    /**
     * set data from database between 2 dates in String
     * if dates are compatible return true and data will be between 2 dates
     * else return false and data do not change
     */
    fun setDataFromTo(fromDate : String, toDate : String) {
        val fromDateL = convertDateToLong(fromDate)
        val toDateL = convertDateToLong(toDate)

        if (fromDateL >= 0 && toDateL >= 0) {
            viewModelScope.launch {
               val data = database.get(fromDateL, toDateL)
                setAmount(data)
            }
        }
    }

    /**
     * set income and expense by data
     */
    private fun setAmount(data : List<Data>) {
        if (data.isNotEmpty()) {
            val dataSize = data.size
            _expense.value = 0.0
            _income.value = 0.0

            for (i in 0 until dataSize) {
                if (data[i].type == true) {
                    _income.value = income.value!! + data[i].amount!!
                } else {
                    _expense.value = expense.value!! + data[i].amount!!
                }
            }
             segmentsInit()
        }
    }

    /**
     * Convert date in String to milliseconds and return it
     * return -1 if it is in bad format
     */
    private fun convertDateToLong(date: String): Long {
        if (!tryDateConvert(date)) {
            return -1
        }
        val dateTime = "$date 00:00:00"
        return getMilliseconds(dateTime)!!

    }
}