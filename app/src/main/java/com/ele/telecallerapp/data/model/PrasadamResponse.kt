package com.ele.telecallerapp.data.model

data class PrasadamResponse(
    val success: Boolean,
    val data: Prasadam
)

data class Prasadam(
    val _id: String,
    val donorName: String,
    val mobile: String,
    val amount: Double,
    val donationDate: String,
    val shippingAddress: String?,
    val loggedByName: String
)
