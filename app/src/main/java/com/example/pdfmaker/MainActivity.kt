package com.example.pdfmaker

import android.os.Bundle
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // lateinit is used as promise that the variable  will be initialised later
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // id of Views
        toolbar = findViewById(R.id.toolbar)

    }

    // Toolbar Setup
    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        // Title set on toolbar
        supportActionBar?.setTitle("PDF MAKER")
        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}