
package com.ele.telecallerapp.network

/* ================= LIST RESPONSES ================= */

data class LeadListResponse(
    val success: Boolean,
    val data: List<LeadDto>
)

data class LeadResponse(
    val success: Boolean,
    val data: LeadDto
)

/* ================= MAIN DTO ================= */

data class LeadDto(
    val _id: String,
    val name: String,
    val phone: String,
    val email: String?,
    val company: String?,
    val campaign: String?,
    val status: String,
    val imageUrl: String?,
    val source: String?,
    val priority: String?,
    val description: String?,   // ✅
    val createdAt: String,
    val lastActivityAt: String?
)

/* ================= REQUEST MODELS ================= */

data class CreateLeadRequest(
    val name: String,
    val email: String?,
    val phone: String,
    val status: String,
    val company: String? = null,
    val campaign: String? = null,
    val source: String? = null,
    val priority: String? = null,
    val description: String? = null // ✅
)

data class UpdateLeadRequest(
    val name: String?,
    val email: String?,
    val phone: String?,
    val company: String?,
    val campaign: String?,
    val status: String?,
    val source: String?,
    val priority: String?,
    val description: String? = null // ✅
)
