package com.example.financnykontrolor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vytvor()
    }

    fun vytvor() {
        var data = Data(0)
        println(data.getData())
    }
}