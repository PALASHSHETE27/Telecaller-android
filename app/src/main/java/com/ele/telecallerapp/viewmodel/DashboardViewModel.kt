

package com.ele.telecallerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ele.telecallerapp.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UiDashboardStats(
    val freshLeads: Int = 0,
    val contacted: Int = 0,
    val interestedLeads: Int = 0,
    val todayIncrease: Int = 0
)

class DashboardViewModel : ViewModel() {

    private val api = RetrofitClient.api

    private val _stats = MutableStateFlow(UiDashboardStats())
    val stats: StateFlow<UiDashboardStats> = _stats

    private val _activities = MutableStateFlow<List<UiActivityItem>>(emptyList())
    val activities: StateFlow<List<UiActivityItem>> = _activities

    fun refresh() {
        viewModelScope.launch {
            try {
                val statsRes = api.getDashboardStats()
                val actRes = api.getDashboardActivities()

                if (statsRes.success) {
                    val d = statsRes.data
                    _stats.value = UiDashboardStats(
                        freshLeads = d.freshLeads,
                        contacted = d.contacted,
                        interestedLeads = d.interestedLeads,
                        todayIncrease = d.todayIncrease
                    )
                }

                if (actRes.success) {
                    _activities.value = actRes.data.map {
                        UiActivityItem(
                            id = it._id,
                            type = it.type,
                            description = it.description ?: "",
                            time = it.createdAt
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    init {
        refresh()
    }
}


