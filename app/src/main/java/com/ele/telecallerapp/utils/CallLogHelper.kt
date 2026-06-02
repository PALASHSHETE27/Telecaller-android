package com.ele.telecallerapp.utils

import android.content.Context
import android.provider.CallLog

data class CallStats(
    val total: Int,
    val incoming: Int,
    val outgoing: Int,
    val missed: Int,
    val firstCallTime: String,
    val lastCallTime: String
)

object CallLogHelper {

    fun getCallStats(context: Context): CallStats {
        val cursor = context.contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            null,
            null,
            null,
            "${CallLog.Calls.DATE} DESC"
        )

        var total = 0
        var incoming = 0
        var outgoing = 0
        var missed = 0
        var firstCall = "—"
        var lastCall = "—"

        cursor?.use {
            if (it.moveToFirst()) {
                lastCall = formatTime(it.getLong(it.getColumnIndexOrThrow(CallLog.Calls.DATE)))
            }

            while (it.moveToNext()) {
                total++
                when (it.getInt(it.getColumnIndexOrThrow(CallLog.Calls.TYPE))) {
                    CallLog.Calls.INCOMING_TYPE -> incoming++
                    CallLog.Calls.OUTGOING_TYPE -> outgoing++
                    CallLog.Calls.MISSED_TYPE -> missed++
                }
                firstCall = formatTime(it.getLong(it.getColumnIndexOrThrow(CallLog.Calls.DATE)))
            }
        }

        return CallStats(total, incoming, outgoing, missed, firstCall, lastCall)
    }

    private fun formatTime(time: Long): String {
        val sdf = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
        return sdf.format(java.util.Date(time))
    }
}
