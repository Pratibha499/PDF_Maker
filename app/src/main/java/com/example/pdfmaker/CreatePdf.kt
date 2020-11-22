package com.example.pdfmaker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.File

class CreatePdf : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pdf)

        /** Request Permission to Read and Write to External Storage **/
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,         // Request permission to read
                Manifest.permission.WRITE_EXTERNAL_STORAGE         // Request permission to write
            ), PackageManager.PERMISSION_GRANTED
        )
    }

    fun createFilesDirectory() {
        // Directory for all files
        val rootPath = File(Environment.getExternalStorageDirectory(), "PDF MAKER Files")
        // If path does not exist
        if (!rootPath.exists()) {
            // will make a directory for it
            rootPath.mkdirs()
        }
        //Creates a new File instance from a parent abstract pathname and a child pathname string.
        val dataFile = File(rootPath, "test file")
    }
}