package com.example.financnykontrolor.fragments.addDataFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.financnykontrolor.database.DataDatabaseDao

/**
 * ViewModelProvider factory for AddDataViewModel
 */
class AddDataViewModelFactory(
    private val dataSource: DataDatabaseDao,
    private val application: Application,
    private val fragment: AddDataFragment) : ViewModelProvider.Factory {

    /**
     * create AddDataViewModel with dataSource, application and fragment and return it
     */
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddDataViewModel::class.java)) {
            return AddDataViewModel(dataSource, application, fragment) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}