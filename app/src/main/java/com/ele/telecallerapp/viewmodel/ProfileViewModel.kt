
package com.ele.telecallerapp.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ele.telecallerapp.network.RetrofitClient
import com.ele.telecallerapp.network.UserProfile
import com.ele.telecallerapp.utils.uriToFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ProfileViewModel : ViewModel() {

    private val api = RetrofitClient.api   // ✅ FIXED


    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile = _profile.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    /* ---------------- LOAD PROFILE ---------------- */

    fun loadProfile() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val res = api.getProfile()
                val user = res.user

                val finalUser = user.copy(
                    employeeId = user.employeeId ?: generateEmployeeId(user._id)
                )

                _profile.value = finalUser
            } catch (e: Exception) {
                e.printStackTrace()
                _message.value = "Failed to load profile"
            } finally {
                _loading.value = false
            }
        }
    }

    /* ---------------- UPDATE PROFILE ---------------- */

    fun updateProfile(name: String, mobile: String, imageUri: Uri?, context: Context) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val namePart = name.toRequestBody("text/plain".toMediaType())
                val mobilePart = mobile.toRequestBody("text/plain".toMediaType())

                val imagePart = imageUri?.let {
                    val file = uriToFile(context, it)
                    val body = file.asRequestBody("image/*".toMediaType())
                    MultipartBody.Part.createFormData("image", file.name, body)
                }

                val res = api.updateProfile(namePart, mobilePart, imagePart)

                val user = res.user
                if (user != null) {
                    val finalUser = user.copy(
                        employeeId = user.employeeId ?: generateEmployeeId(user._id)
                    )
                    _profile.value = finalUser
                }

                _message.value = res.message

            } catch (e: Exception) {
                e.printStackTrace()
                _message.value = "Failed to update profile"
            } finally {
                _loading.value = false
            }
        }
    }

    /* ---------------- EMPLOYEE ID ---------------- */

    private fun generateEmployeeId(userId: String): String {
        return "EMP-" + userId.takeLast(6).uppercase()
    }
}
