package com.ele.telecallerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ele.telecallerapp.network.RetrofitClient
import kotlinx.coroutines.launch

class NotificationViewModel : ViewModel() {

    private val api = RetrofitClient.api

    fun updateNotificationStatus(enabled: Boolean) {
        viewModelScope.launch {
            try {
                api.updateNotificationSettings(
                    mapOf("enabled" to enabled)   // ✅ FIX
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
