
package com.ele.telecallerapp.ui.screens.profile

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ele.telecallerapp.ui.components.ProfilePhotoPicker
import com.ele.telecallerapp.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel()
) {

    val profile by viewModel.profile.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val message by viewModel.message.collectAsState()

    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    LaunchedEffect(profile) {
        profile?.let {
            name = it.name
            mobile = it.mobile ?: ""
        }
    }

    // SUCCESS POPUP

    LaunchedEffect(message) {
        if (!message.isNullOrEmpty() &&
            !message!!.contains("Failed", true)
        ) {
            Toast.makeText(
                context,
                "Profile Updated Successfully",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F8FF))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp, vertical = 20.dp)
        ) {

            // =========================================================
            // PROFILE CARD
            // =========================================================

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 16.dp,
                        shape = RoundedCornerShape(34.dp)
                    ),

                shape = RoundedCornerShape(34.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),

                elevation = CardDefaults.cardElevation(0.dp)
            ) {

                Box(
                    modifier = Modifier.background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF0D47A1),
                                Color(0xFF1976D2),
                                Color(0xFF42A5F5)
                            )
                        )
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 30.dp),

                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        // PROFILE IMAGE

                        Box(
                            modifier = Modifier.size(135.dp),
                            contentAlignment = Alignment.BottomEnd
                        ) {

                            Card(
                                shape = CircleShape,
                                modifier = Modifier.size(120.dp),
                                elevation = CardDefaults.cardElevation(12.dp)
                            ) {

                                ProfilePhotoPicker(
                                    imageUrl = imageUri?.toString()
                                        ?: profile?.profileImage,

                                    onImageSelected = {
                                        imageUri = it
                                    }
                                )
                            }

                            // CAMERA BUTTON

                            Surface(
                                modifier = Modifier.size(46.dp),
                                shape = CircleShape,
                                color = Color.White,
                                shadowElevation = 10.dp
                            ) {

                                Icon(
                                    imageVector = Icons.Default.CameraAlt,
                                    contentDescription = null,
                                    tint = Color(0xFF1565C0),
                                    modifier = Modifier.padding(11.dp)
                                )
                            }
                        }

                        Spacer(Modifier.height(18.dp))

                        Text(
                            text = if (loading)
                                "Loading..."
                            else
                                profile?.name ?: "User",

                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            text = profile?.email ?: "",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.92f)
                        )

                        Spacer(Modifier.height(22.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            // PHONE PILL

                            Surface(
                                shape = RoundedCornerShape(50),
                                color = Color.White.copy(alpha = 0.18f)
                            ) {

                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 14.dp,
                                        vertical = 10.dp
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Icon(
                                        Icons.Default.Phone,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )

                                    Spacer(Modifier.width(6.dp))

                                    Text(
                                        text = "+91 ${profile?.mobile ?: ""}",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                }
                            }

                            // EMPLOYEE ID PILL

                            Surface(
                                shape = RoundedCornerShape(50),
                                color = Color.White.copy(alpha = 0.18f)
                            ) {

                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 14.dp,
                                        vertical = 10.dp
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Icon(
                                        Icons.Default.Badge,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )

                                    Spacer(Modifier.width(6.dp))

                                    Text(
                                        text = profile?.employeeId ?: "--",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(26.dp))

            // =========================================================
            // EDIT PROFILE CARD
            // =========================================================

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(30.dp)
                    ),

                shape = RoundedCornerShape(30.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFEAF4FF)
                ),

                elevation = CardDefaults.cardElevation(0.dp)
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text(
                        text = "Edit Profile",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = "Update your personal details",
                        color = Color(0xFF64748B),
                        fontSize = 14.sp
                    )

                    Spacer(Modifier.height(28.dp))

                    ProfileField(
                        title = "Full Name",
                        value = name,
                        onValueChange = {
                            name = it
                        }
                    )

                    Spacer(Modifier.height(20.dp))

                    ProfileField(
                        title = "Mobile Number",
                        value = mobile,
                        onValueChange = {
                            if (it.length <= 10) {
                                mobile = it.filter { char ->
                                    char.isDigit()
                                }
                            }
                        },
                        keyboardType = KeyboardType.Number,
                        prefix = "+91"
                    )

                    Spacer(Modifier.height(20.dp))

                    ProfileField(
                        title = "Email Address",
                        value = profile?.email ?: "",
                        onValueChange = {},
                        enabled = false
                    )

                    Spacer(Modifier.height(20.dp))

                    ProfileField(
                        title = "Employee ID",
                        value = profile?.employeeId ?: "",
                        onValueChange = {},
                        enabled = false
                    )

                    Spacer(Modifier.height(34.dp))

                    // SAVE BUTTON

                    Button(
                        onClick = {

                            viewModel.updateProfile(
                                name = name,
                                mobile = mobile,
                                imageUri = imageUri,
                                context = context
                            )
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp),

                        shape = RoundedCornerShape(18.dp),

                        enabled = !loading,

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1565C0)
                        )
                    ) {

                        if (loading) {

                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )

                        } else {

                            Text(
                                text = "Save Changes",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    prefix: String? = null
) {

    Column {

        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Color(0xFF334155)
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,

            enabled = enabled,

            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),

            leadingIcon = {
                prefix?.let {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1565C0)
                    )
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color(0xFFD6E4F0),
                    shape = RoundedCornerShape(18.dp)
                ),

            shape = RoundedCornerShape(18.dp),

            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1565C0),
                unfocusedBorderColor = Color(0xFFD6E4F0),
                disabledBorderColor = Color(0xFFD6E4F0),

                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color(0xFFF1F5F9)
            ),

            singleLine = true
        )
    }
}




