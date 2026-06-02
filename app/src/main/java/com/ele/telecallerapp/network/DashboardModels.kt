package com.ele.telecallerapp.network

data class DashboardStatsResponse(
    val success: Boolean,
    val data: DashboardStatsData
)
data class DashboardStatsData(
    val freshLeads: Int,
    val contacted: Int,
    val interestedLeads: Int,
    val todayIncrease: Int
)
