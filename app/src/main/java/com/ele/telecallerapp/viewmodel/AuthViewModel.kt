
package com.ele.telecallerapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ele.telecallerapp.TokenStore
import com.ele.telecallerapp.network.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class AuthViewModel : ViewModel() {

    private val api = RetrofitClient.api

    /* ---------------- REGISTER ---------------- */
    fun register(
        name: String,
        email: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val res = api.register(RegisterRequest(name, email, password))
                onResult(res.success, res.message)

            } catch (e: HttpException) {
                onResult(false, parseError(e))

            } catch (e: Exception) {
                onResult(false, "Network error")
            }
        }
    }

    /* ---------------- VERIFY OTP ---------------- */
    fun verifyOtp(
        email: String,
        otp: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val res = api.verifyOtp(OtpVerifyRequest(email, otp))
                onResult(res.success, res.message)

            } catch (e: HttpException) {
                onResult(false, parseError(e))

            } catch (e: Exception) {
                onResult(false, "Network error")
            }
        }
    }

    /* ---------------- LOGIN ---------------- */
    fun login(
        context: Context,
        email: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val res = api.login(LoginRequest(email, password))

                if (res.success && !res.accessToken.isNullOrEmpty()) {
                    TokenStore.saveToken(context, res.accessToken)
                }

                onResult(res.success, res.message)

            } catch (e: HttpException) {
                onResult(false, parseError(e))

            } catch (e: Exception) {
                onResult(false, "Network error")
            }
        }
    }

    /* ---------------- FORGOT PASSWORD ---------------- */
    fun forgotPassword(
        email: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val res = api.forgotPassword(ForgotPasswordRequest(email))
                onResult(res.success, res.message)

            } catch (e: HttpException) {
                onResult(false, parseError(e))

            } catch (e: Exception) {
                onResult(false, "Network error")
            }
        }
    }

    /* ---------------- RESET PASSWORD ---------------- */
    fun resetPassword(
        email: String,
        otp: String,
        newPassword: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val res = api.resetPassword(
                    ResetPasswordRequest(email, otp, newPassword)
                )
                onResult(res.success, res.message)

            } catch (e: HttpException) {
                onResult(false, parseError(e))

            } catch (e: Exception) {
                onResult(false, "Network error")
            }
        }
    }

    /* ---------------- PARSE BACKEND ERROR ---------------- */
    private fun parseError(e: HttpException): String {
        return try {
            val errorBody = e.response()?.errorBody()?.string()
            JSONObject(errorBody ?: "")
                .optString("message", "Something went wrong")
        } catch (ex: Exception) {
            "Something went wrong"
        }
    }
}