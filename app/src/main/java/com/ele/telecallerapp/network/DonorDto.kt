package com.ele.telecallerapp.network
data class DonorDto(
    val donorName: String,
    val mobile: String,
    val location: String,
    val donorType: String,
    val totalGiven: Double,
    val lastDate: String
)

