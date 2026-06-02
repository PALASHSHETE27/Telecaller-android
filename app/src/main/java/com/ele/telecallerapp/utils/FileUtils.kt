
package com.ele.telecallerapp.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File

fun uriToFile(context: Context, uri: Uri): File {
    val contentResolver = context.contentResolver
    val fileName = getFileName(context, uri) ?: "upload_${System.currentTimeMillis()}.jpg"

    val file = File(context.cacheDir, fileName)

    contentResolver.openInputStream(uri)?.use { inputStream ->
        file.outputStream().use { output ->
            inputStream.copyTo(output)
        }
    } ?: throw IllegalArgumentException("Unable to open URI: $uri")

    return file
}

private fun getFileName(context: Context, uri: Uri): String? {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (it.moveToFirst() && nameIndex != -1) {
            return it.getString(nameIndex)
        }
    }
    return null
}
