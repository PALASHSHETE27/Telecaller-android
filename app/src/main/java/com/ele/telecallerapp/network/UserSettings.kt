package com.ele.telecallerapp.network

data class UserSettings(
    val notifications: Boolean,
    val biometric: Boolean,
    val autoDial: Boolean,
    val recordCalls: Boolean,
    val language: String,
    val region: String
)
