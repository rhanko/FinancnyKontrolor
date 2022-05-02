package com.example.financnykontrolor.fragments.mainFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.financnykontrolor.database.DataDatabaseDao

/**
 * ViewModelProvider factory for MainViewModel
 */
class MainViewModelFactory(
    private val dataSource: DataDatabaseDao,
    private val application: Application,
    private val fragment: MainFragment) : ViewModelProvider.Factory {

    /**
     * create MainViewModel with dataSource and application and return it
     */
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dataSource, application, fragment) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}