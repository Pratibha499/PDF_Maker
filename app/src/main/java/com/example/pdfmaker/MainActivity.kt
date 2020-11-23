package com.example.pdfmaker

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.util.*

class MainActivity : AppCompatActivity() {

    // lateinit is used as promise that the variable will be initialised later
    private lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    lateinit var languageButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /****  id of Views are initialised to the variables  ****/
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        frameLayout = findViewById(R.id.frame)
        coordinatorLayout = findViewById(R.id.coordinator_layout)
        navigationView = findViewById(R.id.navigation_view)
        languageButton = findViewById(R.id.SwitchLanguage)

        // function call
        setUpToolbar()

        /**** Tie the functionality of DrawerLayout and the framework ActionBar  ****/
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_nav_drawer,
            R.string.close_nav_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        languageButton.setOnClickListener {
            showChangelanguage()
        }
    }

    /****    Toolbar Setup    ****/
    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        // Title set on toolbar
        supportActionBar?.setTitle("PDF MAKER")
        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)

        }
        return super.onOptionsItemSelected(item)
    }


    /** Language Change - Hindi & English **/
    fun showChangelanguage() {
        val listlanguage = arrayOf("Hindi", "English")
        val mBuilder = AlertDialog.Builder(this@MainActivity)
        mBuilder.setTitle("Choose Language")
        mBuilder.setSingleChoiceItems(listlanguage, -1) { dialog, which ->
            if (which == 0) {
                setLocate("hi")
                recreate()
            } else if (which == 1) {
                setLocate("en")
                recreate()
            }
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    fun setLocate(Lang: String) {
        val locale = Locale(Lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        val editor = getSharedPreferences("settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

    fun goToCreatePdfActivity() {
        startActivity(Intent(this, CreatePdf::class.java))
    }
}