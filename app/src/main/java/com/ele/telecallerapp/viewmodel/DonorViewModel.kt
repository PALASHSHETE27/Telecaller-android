
package com.ele.telecallerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ele.telecallerapp.network.DonorDto
import com.ele.telecallerapp.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DonorViewModel : ViewModel() {

    private val _donors = MutableStateFlow<List<DonorDto>>(emptyList())
    val donors = _donors.asStateFlow()

    private var allDonors: List<DonorDto> = emptyList()

    // Load donors from API
    fun loadDonors() {
        viewModelScope.launch {
            try {
                val res = RetrofitClient.api.getMyDonors() // returns List<DonorDto>
                allDonors = res
                _donors.value = allDonors
            } catch (e: Exception) {
                e.printStackTrace()
                _donors.value = emptyList()
            }
        }
    }

    // Update donor details
    fun updateDonor(updatedDonor: DonorDto, onResult: (success: Boolean) -> Unit = {}) {
        viewModelScope.launch {
            try {
                val res = RetrofitClient.api.updateDonor(updatedDonor.mobile, updatedDonor)
                if (res.isSuccessful) {
                    // Update local list
                    allDonors = allDonors.map {
                        if (it.mobile == updatedDonor.mobile) updatedDonor else it
                    }
                    _donors.value = allDonors
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                onResult(false)
            } catch (e: HttpException) {
                e.printStackTrace()
                onResult(false)
            }
        }
    }

    // Search donors by name or mobile
    fun search(query: String) {
        _donors.value = allDonors.filter {
            it.donorName.contains(query, ignoreCase = true) ||
                    it.mobile.contains(query)
        }
    }

    // Sort donors by lastDate descending
    fun sortByDate() {
        _donors.value = _donors.value.sortedByDescending { it.lastDate }
    }

    // Filter high value donors (totalGiven >= 1000)
    fun highValue() {
        _donors.value = allDonors.filter { it.totalGiven >= 1000 }
    }

    // Reset list to original
    fun reset() {
        _donors.value = allDonors
    }
}
