package com.ele.telecallerapp.network
data class DonationDto(
    val date: String,
    val donorName: String,
    val mobile: String,
    val location: String,
    val donorType: String,
    val amount: Double,
    val paymentType: String,
    val paymentMode: String
)
