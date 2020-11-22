package com.example.pdfmaker.helper

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract

object RealPathUtil {

    /** Annotation by Android Lint Tool **/
    @SuppressLint("NewApi")
            /** API-19 **/
    fun getPathFromUri(context: Context, uri: Uri): String? {
        var filePath: String? = ""
        if (DocumentsContract.isDocumentUri(context, uri)) {
            val documentId = DocumentsContract.getDocumentId(uri)
        }

        return ""
    }
}