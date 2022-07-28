package com.ecospend.paybybank.shared.helper

import android.content.Context
import java.io.InputStream
import java.nio.charset.Charset

object ResourceHelper {
    fun readAssetFile(context: Context, fileName: String): String {
        val inputStream: InputStream = context.assets.open(fileName)
        val size: Int = inputStream.available()

        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer)
    }

    fun loadJsonFromAssets(context: Context, fileName: String): String {
        return try {
            val file = context.assets.open(fileName)
            val size = file.available()
            val buffer = ByteArray(size)
            file.read(buffer)
            file.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (e: Exception) {
            return ""
        }
    }
}
