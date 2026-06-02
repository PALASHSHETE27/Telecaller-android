package com.ele.telecallerapp.network

data class CallStats(
    val firstCall: String = "--:--",
    val lastCall: String = "--:--",
    val allCalls: Int = 0,
    val connected: Int = 0,
    val incoming: Int = 0,
    val outgoing: Int = 0,
    val missed: Int = 0,
    val goal: Int = 100
)
