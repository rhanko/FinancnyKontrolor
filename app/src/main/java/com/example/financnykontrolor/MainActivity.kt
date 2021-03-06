package com.example.financnykontrolor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.financnykontrolor.database.DataDatabase
import com.example.financnykontrolor.database.DataDatabaseDao
import com.example.financnykontrolor.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

/**
 * Main activity of the program
 */
class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController

    private lateinit var dataSource : DataDatabaseDao

    /**
     * what should create after start activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataSource = DataDatabase.getInstance(application).dataDatabaseDao

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        drawerLayout = binding.drawerLayout

        //colored icons in menu
        val navView: NavigationView = binding.navigationViewBar
        navView.itemIconTintList = null

        navController = this.findNavController(R.id.navigationFragment)

        //for show navigator menu
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        //for open menu item
        NavigationUI.setupWithNavController(binding.navigationViewBar,navController)
    }

    /**
     * For open navigator (menu in top left)
     */
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    /**
     * return Database
     */
    fun getSource() : DataDatabaseDao {
        return dataSource
    }

    var idData : Long = 0

    /**
     *  return to back and idData change back to 0
     */
    override fun onBackPressed() {
        super.onBackPressed()
        idData = 0
    }

}