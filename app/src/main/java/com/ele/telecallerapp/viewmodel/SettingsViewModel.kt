package com.ele.telecallerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ele.telecallerapp.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    private val api = RetrofitClient.api

    private val _notificationsEnabled = MutableStateFlow(true)
    val notificationsEnabled = _notificationsEnabled.asStateFlow()

    private val _status = MutableStateFlow<String?>(null)
    val status = _status.asStateFlow()

    /* ---------- SAVE FCM TOKEN ---------- */
    fun saveFcmToken(token: String) {
        viewModelScope.launch {
            try {
                api.saveFcmToken(mapOf("token" to token))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /* ---------- UPDATE NOTIFICATION ---------- */
    fun updateNotifications(enabled: Boolean) {
        viewModelScope.launch {
            try {
                api.updateNotificationSettings(
                    mapOf("enabled" to enabled)
                )
                _notificationsEnabled.value = enabled
                _status.value = "Updated"
            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = "Failed"
            }
        }
    }
}
