package com.example.financnykontrolor.fragments.showDataFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.financnykontrolor.database.DataDatabaseDao
import kotlinx.coroutines.launch

/**
 * ShowDataViewModel is ViewModel for Fragment ShowData
 * with LiveData
 */
class ShowDataViewModel(
    val database : DataDatabaseDao,
    application: Application) : AndroidViewModel(application) {

    val data = database.getAllData()

    fun deleteData(value : Long) {
        viewModelScope.launch {
            database.delete(value)
        }
    }
}