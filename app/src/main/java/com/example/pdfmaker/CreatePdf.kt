package com.example.pdfmaker

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.pdfmaker.helper.RealPathUtil
import java.io.*

class CreatePdf : AppCompatActivity() {

    // Image URI
    private var file: Uri? = null

    // PDF document
    var pdfDocument: PdfDocument? = null
    val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/Camera/"

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

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), PackageManager.PERMISSION_GRANTED)
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
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
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("onActivityResult: ", "Activity result came out")

        // Multiple Image selection from phone gallery
        val clipData = data!!.clipData

        //Iff user permits
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK && clipData != null) {
            // creates a document
            pdfDocument = PdfDocument()
            // loop till data (image) counter ends
            for (i in 0 until clipData.itemCount) {
                // uri of each data (image)
                file = clipData.getItemAt(i).uri
                // converted to bitmap (an image file format)
                val bitmap = BitmapFactory.decodeFile(uriToFilename(file))
                // create a page description
                val pageInfo = PdfDocument.PageInfo.Builder(1, 2160, i + 1).create()
                // start a Page
                val startPage = pdfDocument!!.startPage(pageInfo)
                // draw something on the page
                startPage.canvas.drawBitmap(bitmap, 0f, 0f, null)
                // finish Page
                pdfDocument!!.finishPage(startPage)
            }
            // location of PDF document and a random name to it
            val pdfFileDir = "/storage/emulated/0/MY_PDF_CONVERTER/$randomName.pdf"
            Log.d("onActivityResult: ", pdfFileDir)
            val pdfFiles = File(pdfFileDir)
            try {
                pdfDocument!!.writeTo(FileOutputStream(pdfFiles))
            } catch (e: IOException) {
                e.printStackTrace()
            }
            // document closed
            pdfDocument!!.close()
        }

        // if single image is picked
        else if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK) {
            file = data.data
            // Repeat the same process for one image
            /** data (image) --> Bitmap --> Page --> PDF Document **/
            val bitmap = BitmapFactory.decodeFile(uriToFilename(file))
            val pdfDocument = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(5184, 3880, 1).create()
            val startPage = pdfDocument.startPage(pageInfo)
            startPage.canvas.drawBitmap(bitmap, 0f, 0f, null)
            pdfDocument.finishPage(startPage)
            val pdfFileDir = "/storage/emulated/0/PDF Maker Files/$randomName.pdf"
            Log.d("onActivityResult: ", pdfFileDir)
            val pdfFiles = File(pdfFileDir)
            try {
                pdfDocument.writeTo(FileOutputStream(pdfFiles))
            } catch (e: IOException) {
                e.printStackTrace()
            }
            pdfDocument.close()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    // function to convert image uri to filename
    private fun uriToFilename(uri: Uri?): String? {
        var path: String? = null
        path = if (Build.VERSION.SDK_INT < 11) {
            // function call from RealPathUtil Object
            RealPathUtil.getRealPathFromURI_BelowAPI11(this, uri)
        } else if (Build.VERSION.SDK_INT < 19) {
            RealPathUtil.getRealPathFromURI_API11to18(this, uri)
        } else {
            RealPathUtil.getPathFromURI_API19(this, uri!!)
        }
        // Path created
        return path
    }

    // Random name assigned to PDF file name
    private val randomName: String
        private get() {
            val AlphaNumericString = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "0123456789"
                    + "abcdefghijklmnopqrstuvxyz")
            val sb = StringBuilder(5)
            for (i in 0..4) {
                val index = (AlphaNumericString.length
                        * Math.random()).toInt()
                sb.append(AlphaNumericString[index])
            }
            return sb.toString()
        }


    override fun onRestart() {
        super.onRestart()
        super.onResume()
        Toast.makeText(this, "PDF Created Successfully", Toast.LENGTH_SHORT).show()
    }


    fun convertToPdf(view: View?) {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_PICK_CODE)
    }

}
