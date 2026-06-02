package com.ele.telecallerapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ele.telecallerapp.viewmodel.AuthViewModel

@Composable
fun OtpVerifyScreen(
    email: String,
    onVerifySuccess: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    var otp by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Verify OTP", style = MaterialTheme.typography.headlineSmall)
                Text("OTP sent to $email", color = Color.Gray)

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = otp,
                    onValueChange = { otp = it },
                    label = { Text("Enter OTP") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        loading = true
                        error = null

                        viewModel.verifyOtp(email, otp) { success, msg ->
                            loading = false
                            if (success) {
                                onVerifySuccess()
                            } else {
                                error = msg
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !loading
                ) {
                    if (loading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp))
                    } else {
                        Text("Verify OTP")
                    }
                }

                error?.let {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(it, color = Color.Red)
                }
            }
        }
    }
}
