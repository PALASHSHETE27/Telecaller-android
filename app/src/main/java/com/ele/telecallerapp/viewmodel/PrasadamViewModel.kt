
package com.ele.telecallerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ele.telecallerapp.data.repository.PrasadamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*


sealed class SubmitState {
    object Idle : SubmitState()
    object Loading : SubmitState()
    object Success : SubmitState()
    data class Error(val msg: String) : SubmitState()
}

class PrasadamViewModel : ViewModel() {

    private val repo = PrasadamRepository()

    private val _submitState = MutableStateFlow<SubmitState>(SubmitState.Idle)
    val submitState: StateFlow<SubmitState> = _submitState

    fun submit(
        donationDate: Date?,
        donorName: String,
        mobile: String,
        amount: Double?,
        address: String?
    ) {
        if (donationDate == null || donorName.isBlank() || mobile.isBlank() || amount == null) {
            _submitState.value = SubmitState.Error("Invalid data")
            return
        }

        viewModelScope.launch {
            try {
                _submitState.value = SubmitState.Loading

                val response = repo.createPrasadam(
                    donationDate,
                    donorName,
                    mobile,
                    amount,
                    address
                )

                if (response.isSuccessful && response.body()?.success == true) {
                    _submitState.value = SubmitState.Success
                } else {
                    _submitState.value =
                        SubmitState.Error(response.errorBody()?.string() ?: "Server error")
                }

            } catch (e: Exception) {
                _submitState.value = SubmitState.Error(e.message ?: "Network error")
            }
        }
    }

    fun resetState() {
        _submitState.value = SubmitState.Idle
    }
}
