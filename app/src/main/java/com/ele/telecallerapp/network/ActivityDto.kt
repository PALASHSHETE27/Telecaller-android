package com.ele.telecallerapp.network

data class ActivityListResponse(
    val success: Boolean,
    val data: List<ActivityDto>
)

data class ActivityResponse(
    val success: Boolean,
    val data: ActivityDto
)

data class ActivityDto(
    val _id: String,
    val type: String,
    val description: String?,
    val duration: Int?,          // ✅ FIX
    val createdAt: String
)

data class CreateActivityRequest(
    val type: String,
    val description: String?,
    val duration: Int? = null    // ✅ FIX (DEFAULT NULL)
)
