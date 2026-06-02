
package com.ele.telecallerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ele.telecallerapp.network.DonationDto
import com.ele.telecallerapp.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DonationViewModel : ViewModel() {

    // ---------- Donation History ----------
    private val _donations = MutableStateFlow<List<DonationDto>>(emptyList())
    val donations = _donations.asStateFlow()

    fun loadDonations(mobile: String) {
        viewModelScope.launch {
            try {
                _donations.value =
                    RetrofitClient.api.getDonationsByDonor(mobile)
            } catch (e: Exception) {
                e.printStackTrace()
                _donations.value = emptyList()
            }
        }
    }

    // ---------- Create Donation ----------
    fun submitDonation(donation: DonationDto) {
        viewModelScope.launch {
            try {
                val res = RetrofitClient.api.createDonation(donation)
                if (res.isSuccessful) {
                    println("Donation saved successfully")
                } else {
                    println("Error: ${res.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
