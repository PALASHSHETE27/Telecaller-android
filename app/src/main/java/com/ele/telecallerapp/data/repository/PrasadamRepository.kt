package com.ele.telecallerapp.data.repository

import com.ele.telecallerapp.network.RetrofitClient
import java.text.SimpleDateFormat
import com.ele.telecallerapp.network.PrasadamRequest
import java.util.*

class PrasadamRepository {

    private val api = RetrofitClient.api
    private val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    suspend fun createPrasadam(
        donationDate: Date,
        donorName: String,
        mobile: String,
        amount: Double,
        shippingAddress: String?
    ) = api.createPrasadam(
        PrasadamRequest(
            donationDate = formatter.format(donationDate),
            donorName = donorName,
            mobile = mobile,
            amount = amount,
            shippingAddress = shippingAddress
        )
    )
}
