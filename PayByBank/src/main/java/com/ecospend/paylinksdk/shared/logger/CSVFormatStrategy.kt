package com.ecospend.paybybank.shared.logger

import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.LogStrategy
import com.orhanobut.logger.Logger
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CSVFormatStrategy private constructor(builder: Builder) : FormatStrategy {

    companion object {
        private val NEW_LINE = System.getProperty("line.separator")
        private const val SEPARATOR = ","
        private const val MIN_STACK_OFFSET = 5
        fun builder(): Builder {
            return Builder()
        }
    }

    private val methodCount: Int
    private val methodOffset: Int
    private val showThreadInfo: Boolean
    private val date: Date
    private val dateFormat: SimpleDateFormat
    private val tag: String?
    private val logStrategy: LogStrategy?

    init {
        methodCount = builder.methodCount
        methodOffset = builder.methodOffset
        showThreadInfo = builder.showThreadInfo
        date = builder.date ?: Date()
        dateFormat = builder.dateFormat ?: SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.UK)
        logStrategy = builder.logStrategy
        tag = builder.tag
    }

    override fun log(priority: Int, onceOnlyTag: String?, message: String) {
        var message = message
        date.time = System.currentTimeMillis()
        val builder = StringBuilder()

        // tag
        builder.append("[")
        builder.append(tag)
        builder.append("]")
        // level
        builder.append(SEPARATOR)
        builder.append("[")
        builder.append(logLevel(priority))
        builder.append("]")
        // human-readable date/time
        builder.append(SEPARATOR)
        builder.append("[")
        builder.append(dateFormat.format(date))
        builder.append("]")
        // trace
        if (showThreadInfo) {
            val logTraceModel = getThreadContent(methodCount)
            if (logTraceModel != null) {
                builder.append(SEPARATOR)
                builder.append("Func: ")
                builder.append(logTraceModel.methodName)
                builder.append(SEPARATOR)
                builder.append("File: ")
                builder.append(logTraceModel.className)
                builder.append(SEPARATOR)
                builder.append("L: ")
                builder.append(logTraceModel.lineNumber)
            }
        }
        builder.append(SEPARATOR)
        builder.append("Message: ")
        if (message.contains(SEPARATOR)) {
            message = message.replace(SEPARATOR, "")
        }
        builder.append(message)
        // new line
        builder.append(NEW_LINE)
        logStrategy?.log(priority, tag, builder.toString())
    }

    private fun logLevel(value: Int): String {
        return when (value) {
            Logger.VERBOSE -> "VERBOSE"
            Logger.DEBUG -> "DEBUG"
            Logger.INFO -> "INFO"
            Logger.WARN -> "WARN"
            Logger.ERROR -> "ERROR"
            Logger.ASSERT -> "ASSERT"
            else -> "UNKNOWN"
        }
    }

    private fun getSimpleClassName(name: String): String {
        val lastIndex = name.lastIndexOf(".")
        return name.substring(lastIndex + 1)
    }

    private fun getThreadContent(methodCount: Int): LogTraceModel? {
        var methodCount = methodCount
        val trace =
            Thread.currentThread().stackTrace
        val level = ""
        val stackOffset = getStackOffset(trace) + methodOffset

        // corresponding method count with the current stack may exceeds the stack trace. Trims the count
        if (methodCount + stackOffset > trace.size) {
            methodCount = trace.size - stackOffset - 1
        }
        val logTraceModel: LogTraceModel
        for (i in methodCount downTo 1) {
            val stackIndex = i + stackOffset
            if (stackIndex >= trace.size) {
                continue
            }
            val fullClassName = trace[stackIndex].className
            val simpleClassName =
                fullClassName.substring(fullClassName.lastIndexOf('.') + 1)
            logTraceModel = LogTraceModel(
                simpleClassName,
                trace[stackIndex].methodName,
                trace[stackIndex].lineNumber
            )
            return logTraceModel
        }
        return null
    }

    internal inner class LogTraceModel(
        var className: String,
        var methodName: String,
        var lineNumber: Int
    )

    /**
     * Determines the starting index of the stack trace, after method calls made by this class.
     *
     * @param trace the stack trace
     * @return the stack offset
     */
    private fun getStackOffset(trace: Array<StackTraceElement>): Int {
        var i = MIN_STACK_OFFSET
        while (i < trace.size) {
            val e = trace[i]
            val name = e.className
            if (name != "com.orhanobut.logger.LoggerPrinter" && name != Logger::class.java.name && name != Logger::class.java.name) {
                return --i
            }
            i++
        }
        return -1
    }

    class Builder {
        var methodCount = 2
        var methodOffset = 0
        var showThreadInfo = true
        var date: Date? = null
        var dateFormat: SimpleDateFormat? = null
        var logStrategy: LogStrategy? = null
        var tag: String? = null

        fun date(date: Date?): Builder {
            this.date = date
            return this
        }

        fun dateFormat(dateFormat: SimpleDateFormat?): Builder {
            this.dateFormat = dateFormat
            return this
        }

        fun methodCount(methodCount: Int): Builder {
            this.methodCount = methodCount
            return this
        }

        fun methodOffset(methodOffset: Int): Builder {
            this.methodOffset = methodOffset
            return this
        }

        fun showThreadInfo(showThread: Boolean): Builder {
            showThreadInfo = showThread
            return this
        }

        fun logStrategy(logStrategy: LogStrategy): Builder {
            this.logStrategy = logStrategy
            return this
        }

        fun tag(tag: String?): Builder {
            this.tag = tag
            return this
        }

        fun build(): CSVFormatStrategy {
            return CSVFormatStrategy(this)
        }
    }
}
