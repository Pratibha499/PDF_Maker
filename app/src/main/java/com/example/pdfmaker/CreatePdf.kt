package com.example.pdfmaker

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.*

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

        // Function call
        createFilesDirectory()
    }

    private fun createFilesDirectory() {
        // Directory for all files
        val rootPath = File(Environment.getExternalStorageDirectory(), "PDF MAKER Files")
        // If path does not exist
        if (!rootPath.exists()) {
            // will make a directory for it
            rootPath.mkdirs()
        }
        //Creates a new File instance from a parent abstract pathname and a child pathname string.
        val dataFile = File(rootPath, "test file")
        // if current state of the primary "external" storage device != state at which media is present and mounted
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            // Toast is a message in the form of popup to the user
            Toast.makeText(this, "Cannot use External Storage", Toast.LENGTH_SHORT)
            finish()
            return
        }
        try {
            val mOutput = FileOutputStream(dataFile, false)
            val data = "DATA"
            mOutput.write(data.toByteArray())
            mOutput.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        try {
            val mInput = FileInputStream(dataFile)

            val data = ByteArray(128)
            mInput.read(data)
            mInput.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        dataFile.delete()
    }

    // object that is common to all instances of this class like static in java
    companion object {
        // Assume any positive integer type Number
        const val IMAGE_PICK_CODE = 1

    }


    /** Check whether Permission granted by user **/
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("onActivityResult: ", "Activity result came out")

        // Multiple Image selection from phone gallery
        val clipData = data!!.clipData
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK)

            super.onActivityResult(requestCode, resultCode, data)
    }
}