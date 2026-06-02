package com.ele.telecallerapp.data.repository

import android.content.Context
import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import android.provider.CallLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*
import com.ele.telecallerapp.network.CallStats

class CallStatsRepository(private val context: Context) {

    private val _stats = MutableStateFlow(CallStats())
    val stats = _stats.asStateFlow()

    private val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

    private val observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean) {
            loadStats()
        }
    }

    init {
        context.contentResolver.registerContentObserver(
            CallLog.Calls.CONTENT_URI,
            true,
            observer
        )
        loadStats()
    }

    private fun loadStats() {
        val todayStart = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.timeInMillis

        val cursor = context.contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            null,
            "${CallLog.Calls.DATE} >= ?",
            arrayOf(todayStart.toString()),
            "${CallLog.Calls.DATE} DESC"
        ) ?: return

        var incoming = 0
        var outgoing = 0
        var missed = 0
        var connected = 0
        var firstCallTime: String? = null
        var lastCallTime: String? = null

        cursor.use {
            while (it.moveToNext()) {
                val type = it.getInt(it.getColumnIndexOrThrow(CallLog.Calls.TYPE))
                val date = it.getLong(it.getColumnIndexOrThrow(CallLog.Calls.DATE))
                val duration = it.getLong(it.getColumnIndexOrThrow(CallLog.Calls.DURATION))

                val timeFormatted = dateFormat.format(Date(date))
                if (lastCallTime == null) lastCallTime = timeFormatted
                firstCallTime = timeFormatted

                when (type) {
                    CallLog.Calls.INCOMING_TYPE -> incoming++
                    CallLog.Calls.OUTGOING_TYPE -> outgoing++
                    CallLog.Calls.MISSED_TYPE -> missed++
                }
                if (duration > 0) connected++
            }
        }

        _stats.value = CallStats(
            firstCall = firstCallTime ?: "--:--",
            lastCall = lastCallTime ?: "--:--",
            allCalls = incoming + outgoing + missed,
            connected = connected,
            incoming = incoming,
            outgoing = outgoing,
            missed = missed
        )
    }
}
