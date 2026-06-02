package com.ele.telecallerapp.viewmodel

data class UiLead(
    val id: String,
    val name: String,
    val phone: String,
    val status: String,
    val company: String?,
    val imageUrl: String?,
    val createdAt: String,
    val lastActivityAt: String?
)
