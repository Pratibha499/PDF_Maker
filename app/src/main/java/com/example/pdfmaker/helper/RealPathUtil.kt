package com.example.pdfmaker.helper

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore

object RealPathUtil {

    /** Annotation by Android Lint Tool **/
    @SuppressLint("NewApi")

            /** API-19 **/
    fun getPathFromURI_API19(context: Context, uri: Uri): String? {
        var filePath: String? = ""
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // Will return "image:x*"
            val documentId = DocumentsContract.getDocumentId(uri)
            // Split at colon, use second item in the array
            val idArraySplit = documentId.split(":").toTypedArray()
            if (idArraySplit.size == 2) {
                val id = idArraySplit[1]
                val column = arrayOf(MediaStore.Images.Media.DATA)
                // where id is equal to
                val sel = MediaStore.Images.Media._ID + "=?"
                // Media provider to access all Image type files on the External Storage
                // contentResolver used to access data in content provider
                val cursor = context.contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,     // The content:// style URI for the "primary" external storage volume
                    column, sel, arrayOf(id), null
                )
                val columnIndex = cursor!!.getColumnIndex(column[0])
                // move the cursor to first row
                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(columnIndex)
                }
                // cursor is closed
                cursor.close()
            }
        } else {
            filePath = uri.path
        }
        return filePath
    }


    @SuppressLint("NewApi")

            /** API - 11 to 18 **/
    fun getRealPathFromURI_API11to18(context: Context?, contentUri: Uri?): String? {
        val projection =
            arrayOf(MediaStore.Images.Media.DATA)  // projection is an array of columns that should be included for each row retrieved.
        var result: String? = null

        return ""
    }

}