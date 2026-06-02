package com.ele.telecallerapp.utils

import java.time.Instant
import java.time.temporal.ChronoUnit

fun timeAgo(iso: String?): String {
    if (iso == null) return "Added today"

    val time = Instant.parse(iso)
    val now = Instant.now()

    val mins = ChronoUnit.MINUTES.between(time, now)
    val hrs = ChronoUnit.HOURS.between(time, now)
    val days = ChronoUnit.DAYS.between(time, now)

    return when {
        mins < 60 -> "Last call ${mins}m ago"
        hrs < 24 -> "Last call ${hrs}h ago"
        else -> "Last call ${days}d ago"
    }
}
