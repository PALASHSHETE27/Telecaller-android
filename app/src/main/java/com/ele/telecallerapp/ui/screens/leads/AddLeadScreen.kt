
package com.ele.telecallerapp.ui.screens.leads

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ele.telecallerapp.viewmodel.AddLeadViewModel
import java.io.File
import androidx.compose.foundation.text.KeyboardOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLeadScreen(
    onDone: () -> Unit
) {

    val vm: AddLeadViewModel = viewModel()

    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Fresh") }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var showPicker by remember {
        mutableStateOf(false)
    }

    val statusOptions = listOf(
        "Fresh",
        "Contacted",
        "Interested",
        "Callback",
        "Closed"
    )

    var statusDropdownExpanded by remember {
        mutableStateOf(false)
    }

    // ---------------- VALIDATIONS ----------------

    val isPhoneValid = phone.length == 10

    val isEmailValid = email.isBlank() ||
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    val isFormValid =
        name.isNotBlank() &&
                isPhoneValid &&
                isEmailValid

    // ---------------- CAMERA ----------------

    val photoFile = remember {
        File(
            context.cacheDir,
            "lead_${System.currentTimeMillis()}.jpg"
        )
    }

    val cameraUri = remember {
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            photoFile
        )
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { success ->

            if (success) {
                imageUri = cameraUri
            }
        }

    val galleryLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->

            imageUri = uri
        }

    // ---------------- IMAGE PICKER ----------------

    if (showPicker) {

        ModalBottomSheet(
            onDismissRequest = {
                showPicker = false
            },

            shape = RoundedCornerShape(
                topStart = 28.dp,
                topEnd = 28.dp
            )
        ) {

            ListItem(
                headlineContent = {
                    Text("Open Camera")
                },

                leadingContent = {
                    Icon(Icons.Default.CameraAlt, null)
                },

                modifier = Modifier.clickable {
                    showPicker = false
                    cameraLauncher.launch(cameraUri)
                }
            )

            ListItem(
                headlineContent = {
                    Text("Choose from Gallery")
                },

                leadingContent = {
                    Icon(Icons.Default.PhotoLibrary, null)
                },

                modifier = Modifier.clickable {
                    showPicker = false
                    galleryLauncher.launch("image/*")
                }
            )

            Spacer(Modifier.height(30.dp))
        }
    }

    // ---------------- BUTTON ANIMATION ----------------

    val buttonAlpha by animateFloatAsState(
        targetValue = if (isFormValid) 1f else 0.65f,

        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        ),

        label = ""
    )

    // ---------------- UI ----------------

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),

            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = 28.dp,
                bottom = 40.dp
            ),

            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            // ---------------- HEADER ----------------

            item {

                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically()
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .shadow(
                                    elevation = 18.dp,
                                    shape = CircleShape
                                )
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        listOf(
                                            Color(0xFF38BDF8),
                                            Color(0xFF7DD3FC)
                                        )
                                    )
                                )
                                .clickable(
                                    indication = null,
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    }
                                ) {
                                    showPicker = true
                                },

                            contentAlignment = Alignment.Center
                        ) {

                            if (imageUri != null) {

                                AsyncImage(
                                    model = imageUri,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )

                            } else {

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Icon(
                                        Icons.Default.CameraAlt,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(36.dp)
                                    )

                                    Spacer(Modifier.height(4.dp))

                                    Text(
                                        "Add Photo",
                                        color = Color.White,
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(22.dp))

                        Text(
                            text = "Create Lead",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            text = "Add customer information",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }

            // ---------------- FORM CARD ----------------

            item {

                Card(
                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(32.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE0F2FE)
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(22.dp),

                        verticalArrangement = Arrangement.spacedBy(18.dp)
                    ) {

                        ModernField(
                            value = name,
                            onValueChange = {
                                name = it
                            },
                            label = "Full Name",
                            icon = Icons.Default.Person
                        )

                        // ---------------- PHONE ----------------

                        OutlinedTextField(
                            value = phone,

                            onValueChange = {

                                val filtered = it.filter { char ->
                                    char.isDigit()
                                }

                                if (filtered.length <= 10) {
                                    phone = filtered
                                }
                            },

                            label = {
                                Text("Phone Number")
                            },

                            leadingIcon = {
                                Text(
                                    "+91",
                                    fontWeight = FontWeight.SemiBold
                                )
                            },

                            trailingIcon = {
                                Icon(
                                    Icons.Default.Phone,
                                    contentDescription = null
                                )
                            },

                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),

                            singleLine = true,

                            modifier = Modifier.fillMaxWidth(),

                            shape = RoundedCornerShape(18.dp),

                            isError = phone.isNotBlank() && !isPhoneValid,

                            supportingText = {

                                if (phone.isNotBlank() && !isPhoneValid) {

                                    Text(
                                        "Phone number must be 10 digits"
                                    )
                                }
                            },

                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF0EA5E9),
                                unfocusedBorderColor = Color.Transparent,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            )
                        )

                        // ---------------- EMAIL ----------------

                        ModernField(
                            value = email,
                            onValueChange = {
                                email = it
                            },
                            label = "Email Address",
                            icon = Icons.Default.Email,
                            keyboardType = KeyboardType.Email,
                            isError = email.isNotBlank() && !isEmailValid,
                            errorText = "Enter valid email address"
                        )

                        ModernField(
                            value = company,
                            onValueChange = {
                                company = it
                            },
                            label = "Company",
                            icon = Icons.Default.Work
                        )

                        // ---------------- STATUS ----------------

                        Column {

                            Text(
                                text = "Lead Status",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Gray
                            )

                            Spacer(Modifier.height(8.dp))

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        statusDropdownExpanded =
                                            !statusDropdownExpanded
                                    },

                                shape = RoundedCornerShape(18.dp),

                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                )
                            ) {

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            horizontal = 16.dp,
                                            vertical = 18.dp
                                        ),

                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = Color(0xFF0EA5E9)
                                    )

                                    Spacer(Modifier.width(12.dp))

                                    Text(
                                        text = status,
                                        modifier = Modifier.weight(1f),
                                        fontWeight = FontWeight.Medium
                                    )

                                    Icon(
                                        Icons.Default.ArrowDropDown,
                                        contentDescription = null
                                    )

                                    DropdownMenu(
                                        expanded = statusDropdownExpanded,

                                        onDismissRequest = {
                                            statusDropdownExpanded = false
                                        }
                                    ) {

                                        statusOptions.forEach {

                                            DropdownMenuItem(
                                                text = {
                                                    Text(it)
                                                },

                                                onClick = {
                                                    status = it
                                                    statusDropdownExpanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // ---------------- BUTTON ----------------

            item {

                Spacer(Modifier.height(4.dp))

                Button(
                    onClick = {

                        vm.create(
                            name = name,
                            email = email.ifBlank { null },
                            phone = "+91$phone",
                            status = status,
                            company = company.ifBlank { null },
                            description = null,
                            imageUri = imageUri,
                            onDone = onDone
                        )
                    },

                    enabled = isFormValid,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .alpha(buttonAlpha),

                    shape = RoundedCornerShape(22.dp),

                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp
                    ),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0EA5E9)
                    )
                ) {

                    Text(
                        text = "Save Lead",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ModernField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    minLines: Int = 1,
    isError: Boolean = false,
    errorText: String = ""
) {

    OutlinedTextField(
        value = value,

        onValueChange = onValueChange,

        label = {
            Text(label)
        },

        leadingIcon = {
            Icon(
                icon,
                contentDescription = null
            )
        },

        modifier = Modifier.fillMaxWidth(),

        singleLine = singleLine,

        minLines = minLines,

        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),

        shape = RoundedCornerShape(18.dp),

        isError = isError,

        supportingText = {

            if (isError) {

                Text(errorText)
            }
        },

        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF0EA5E9),
            unfocusedBorderColor = Color.Transparent,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}



