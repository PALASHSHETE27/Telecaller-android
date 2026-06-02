package com.ele.telecallerapp.network

data class PrasadamRequest(
    val donationDate: String,
    val donorName: String,
    val mobile: String,
    val amount: Double,
    val shippingAddress: String?
)
