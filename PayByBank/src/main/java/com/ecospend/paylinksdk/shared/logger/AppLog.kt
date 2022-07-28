package com.ecospend.paybybank.shared.logger

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import com.ecospend.paybybank.BuildConfig
import com.ecospend.paybybank.shared.Config
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.DiskLogAdapter
import com.orhanobut.logger.DiskLogStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.Logger.addLogAdapter
import java.io.File
import java.io.FileWriter
import java.io.IOException

object AppLog {

    private const val logTag = "PaylinkMobileSDK"
    private const val fileName = "log"
    private const val fileNameTemplate = "%s_%s.csv"
    private const val fileCount = 1
    private const val maxLogRom = 10
    private var isDebug: Boolean = true
    private var maxLogFileCount: Int = 1
    private lateinit var path: String
    var isInit = false

    fun init(context: Context) {
        isInit = true
        this.path = context.getExternalFilesDir(null)?.absolutePath + Config.Log.DIRECTORY_NAME
        this.isDebug = BuildConfig.DEBUG

        // Write console only in debug mode.
        if (isDebug) {
            addLogAdapter(AndroidLogAdapter())
        }

        val file = File(path)
        if (!file.exists()) {
            file.mkdir()
        }
        val ht = HandlerThread("log.$path")
        ht.start()

        val handler = WriteHandler(
            looper = ht.looper,
            maxLogRom = this.maxLogRom * 1024 * 1000,
            maxLogFileSize = maxLogFileCount,
            getLogFile = { maxLogRom, maxLogFileSize ->
                getLogFile(maxLogRom, maxLogFileSize)
            },
            writeLog = { fileWriter, content ->
                writeLog(fileWriter, content)
            }
        )
        val csvFormatStrategy = CSVFormatStrategy.builder()
            .tag(logTag)
            .methodCount(1)
            .logStrategy(DiskLogStrategy(handler))
            .build()

        addLogAdapter(DiskLogAdapter(csvFormatStrategy))
    }

    fun i(message: String, vararg args: Any?) {
        Logger.i(message, args)
    }

    fun d(message: String, vararg args: Any?) {
        Logger.d(message, *args)
    }

    fun d(message: Any?) {
        Logger.d(message)
    }

    fun e(message: String, vararg args: Any?) {
        Logger.e(message, *args)
    }

    fun e(throwable: Throwable?, message: String, vararg args: Any?) {
        Logger.e(throwable, message, *args)
    }

    fun v(message: String, vararg args: Any?) {
        Logger.v(message, *args)
    }

    fun w(message: String, vararg args: Any?) {
        Logger.w(message, *args)
    }

    /**
     * Tip: Use this for exceptional situations to log
     * ie: Unexpected errors etc
     */
    fun wtf(message: String, vararg args: Any?) {
        Logger.wtf(message, *args)
    }

    /**
     * Formats the given json content and print it
     */
    fun json(json: String?) {
        Logger.json(json)
    }

    /**
     * Formats the given xml content and print it
     */
    fun xml(xml: String?) {
        Logger.xml(xml)
    }

    private fun getLogFile(maxLogRom: Int, maxLogFileSize: Int): File {
        val folder = File(path)
        if (!folder.exists()) {
            folder.mkdirs()
        }

        var newFileCount: Int = fileCount

        var newFile: File
        var existingFile: File? = null
        newFile = File(folder, String.format(fileNameTemplate, fileName, newFileCount))

        while (newFile.exists()) {
            existingFile = newFile
            newFileCount++
            newFile = File(folder, String.format(fileNameTemplate, fileName, newFileCount))
        }

        if (existingFile != null) {
            if (existingFile.length() >= maxLogRom) {
                delete(maxLogFileSize)
                return newFile
            }
            return existingFile
        }

        return newFile
    }

    private fun delete(maxLogFileSize: Int) {
        val files: Array<File> = getFiles() ?: return
        if (files.size > maxLogFileSize - 1) {
            getLastFile(files)?.delete()
        }
    }

    private fun getLastFile(files: Array<File>): File? {
        var minTime = 0L
        var path: String? = null
        for (i in files.indices) {
            val time = files[i].lastModified()
            if (i == 0) {
                path = files[i].absolutePath
                minTime = time
            }
            if (minTime > time) {
                minTime = time
                path = files[i].absolutePath
            }
        }
        if (path == null) {
            return null
        }
        return File(path)
    }

    private fun getFiles(): Array<File>? {
        val file = File(path)
        return file.listFiles()
    }

    private fun writeLog(fileWriter: FileWriter, content: String) {
        print("[log>>>>] $content")
        fileWriter.append(content)
    }
}

class WriteHandler(
    looper: Looper,
    private val maxLogRom: Int,
    private val maxLogFileSize: Int,
    val getLogFile: (maxLogRom: Int, maxLogFileSize: Int) -> File,
    val writeLog: (fileWriter: FileWriter, content: String) -> Unit
) : Handler(looper) {

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        val content = msg.obj as String
        var fileWriter: FileWriter? = null
        val logFile: File = getLogFile(maxLogRom, maxLogFileSize)
        try {
            fileWriter = FileWriter(logFile, true)
            writeLog(fileWriter, content)
            fileWriter.flush()
            fileWriter.close()
        } catch (e: IOException) {
            if (fileWriter != null) {
                try {
                    fileWriter.flush()
                    fileWriter.close()
                } catch (e1: IOException) { /* fail silently */
                }
            }
        }
    }
}
