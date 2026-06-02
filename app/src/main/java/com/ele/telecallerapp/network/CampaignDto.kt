package com.ele.telecallerapp.network

data class CampaignDto(
    val _id: String,
    val title: String,
    val status: String,
    val audience: String,
    val totalLeads: Int,
    val calledLeads: Int,
    val startDate: String,
    val dueDate: String
)
