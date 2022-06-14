package com.example.financnykontrolor.fragments.addDataFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.financnykontrolor.database.DataDatabaseDao


/**
 * ViewModelProvider factory for AddDataViewModel
 *
 * @property dataSource database
 * @property application application of the activity
 * @property fragment fragment of AddDataFragment
 */
class AddDataViewModelFactory(
    private val dataSource: DataDatabaseDao,
    private val application: Application,
    private val fragment: AddDataFragment) : ViewModelProvider.Factory {

    /**
     * create AddDataViewModel
     *
     * @param T type of ViewModel
     * @param modelClass model of the class
     * @return view model class
     */
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddDataViewModel::class.java)) {
            return AddDataViewModel(dataSource, application, fragment) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}