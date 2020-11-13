package com.example.pdfmaker

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout

class MainActivity : AppCompatActivity() {

    // lateinit is used as promise that the variable will be initialised later
    private lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // id of Views are initialised to the variables
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)


        // function call
        setUpToolbar()

        val ActionBarDrawerToggle: ActionBarDrawerToggle(this, drawerLayout, )
    }

    /*
           Toolbar Setup
    */
    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        // Title set on toolbar
        supportActionBar?.setTitle("PDF MAKER")
        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}