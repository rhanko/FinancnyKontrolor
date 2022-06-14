package com.example.financnykontrolor.fragments.showDataFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.financnykontrolor.database.DataDatabaseDao

/**
 * ViewModelProvider factory for ShowDataViewModel
 *
 * @property dataSource database
 * @property application application of the activity
 */
class ShowDataViewModelFactory(
    private val dataSource: DataDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {

    /**
     * create ShowDataViewModel with dataSource and application and return it
     *
     * @param T type of class
     * @param modelClass model of the class
     * @return view model class
     */
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShowDataViewModel::class.java)) {
            return ShowDataViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

