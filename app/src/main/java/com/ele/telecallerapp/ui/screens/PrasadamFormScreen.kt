

package com.ele.telecallerapp.ui.screens

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ele.telecallerapp.viewmodel.PrasadamViewModel
import com.ele.telecallerapp.viewmodel.SubmitState
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrasadamFormScreen(
    telecallerName: String,
    onBack: () -> Unit
) {

    val vm: PrasadamViewModel = viewModel()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val formatter = remember {
        SimpleDateFormat("dd MMM yyyy", Locale.US)
    }

    var prasadamDate by remember { mutableStateOf<Date?>(null) }
    var prasadamName by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    val submitState by vm.submitState.collectAsState()

    val calendar = Calendar.getInstance()

    val datePicker = DatePickerDialog(
        context,
        { _, y, m, d ->
            calendar.set(y, m, d)
            prasadamDate = calendar.time
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    LaunchedEffect(submitState) {
        when (submitState) {

            is SubmitState.Success -> {
                Toast.makeText(context, "Prasadam Saved Successfully 🙏", Toast.LENGTH_SHORT).show()
                vm.resetState()
                onBack()
            }

            is SubmitState.Error -> {
                Toast.makeText(context, "Failed ❌", Toast.LENGTH_SHORT).show()
                vm.resetState()
            }

            else -> Unit
        }
    }

    Scaffold(
        containerColor = Color(0xFFF3F5FA),

        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    vm.submit(
                        donationDate = prasadamDate,
                        donorName = prasadamName,
                        mobile = mobile,
                        amount = amount.toDoubleOrNull(),
                        address = address
                    )
                },
                shape = RoundedCornerShape(22.dp),
                containerColor = Color(0xFF2563EB),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .height(60.dp)
            ) {

                if (submitState is SubmitState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(Icons.Default.VolunteerActivism, null, tint = Color.White)
                    Spacer(Modifier.width(10.dp))
                    Text("Save Prasadam", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(padding)
        ) {

            // ================= HEADER =================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF111827),
                                Color(0xFF312E81),
                                Color(0xFF4F46E5)
                            )
                        )
                    )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Top
                ) {

                    Text(
                        "TELECALLER PANEL",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp,
                        letterSpacing = 4.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(10.dp))

                    // QUOTE (kept separate space so NO overlap)
                    Text(
                        "\"Serve selflessly, without expectation\"",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 12.sp
                    )

                    Spacer(Modifier.height(18.dp))

                    // TITLE + LOGO ROW
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(modifier = Modifier.weight(1f)) {

                            Text(
                                "PRASADAM\nCOLLECTION",
                                color = Color.White,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.ExtraBold,
                                lineHeight = 40.sp
                            )
                        }

                        Spacer(Modifier.width(16.dp))

                        Box(
                            modifier = Modifier
                                .size(92.dp)
                                .clip(RoundedCornerShape(28.dp))
                                .background(Color.White.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("🙏", fontSize = 36.sp)
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    "SEVA",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }
            }

            // ================= FORM =================
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-40).dp)
                    .padding(horizontal = 18.dp),
                shape = RoundedCornerShape(34.dp),
                colors = CardDefaults.cardColors(Color.White),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {

                Column(Modifier.padding(22.dp)) {

                    ModernField(
                        label = "Prasadam Date",
                        value = prasadamDate?.let { formatter.format(it) } ?: "",
                        icon = Icons.Default.CalendarMonth,
                        readOnly = true,
                        onClick = { datePicker.show() }
                    )

                    Spacer(Modifier.height(18.dp))

                    ModernField(
                        label = "Prasadam Name",
                        value = prasadamName,
                        icon = Icons.Default.Person,
                        onValueChange = { prasadamName = it }
                    )

                    Spacer(Modifier.height(18.dp))

                    ModernField(
                        label = "Mobile Number",
                        value = mobile,
                        icon = Icons.Default.Phone,
                        keyboardType = KeyboardType.Phone,
                        onValueChange = { mobile = it }
                    )

                    Spacer(Modifier.height(18.dp))

                    ModernField(
                        label = "Prasadam Amount",
                        value = amount,
                        icon = Icons.Default.CurrencyRupee,
                        keyboardType = KeyboardType.Number,
                        onValueChange = { amount = it }
                    )

                    Spacer(Modifier.height(18.dp))

                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        shape = RoundedCornerShape(24.dp),
                        label = { Text("Address") },
                        leadingIcon = {
                            Icon(Icons.Default.LocationOn, null)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF2563EB),
                            unfocusedBorderColor = Color(0xFFE5E7EB),
                            focusedContainerColor = Color(0xFFF9FAFB),
                            unfocusedContainerColor = Color(0xFFF9FAFB)
                        )
                    )

                    Spacer(Modifier.height(24.dp))

                    // ================= PREVIEW =================
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(30.dp),
                        colors = CardDefaults.cardColors(Color(0xFF2563EB))
                    ) {

                        Column(Modifier.padding(24.dp)) {

                            Text(
                                "Prasadam Preview",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 13.sp
                            )

                            Spacer(Modifier.height(10.dp))

                            Text(
                                "₹ ${amount.ifBlank { "0" }}",
                                color = Color.White,
                                fontSize = 34.sp,
                                fontWeight = FontWeight.ExtraBold
                            )

                            Spacer(Modifier.height(6.dp))

                            Text(
                                if (amount.toDoubleOrNull() != null)
                                    "Thank you 🙏"
                                else
                                    "Enter prasadam amount",
                                color = Color.White.copy(alpha = 0.85f)
                            )
                        }
                    }

                    Spacer(Modifier.height(100.dp))
                }
            }
        }
    }
}

/* FIELD COMPONENT */
@Composable
fun ModernField(
    label: String,
    value: String,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false,
    onClick: (() -> Unit)? = null,
    onValueChange: (String) -> Unit = {}
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        readOnly = readOnly,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        label = { Text(label) },
        leadingIcon = { Icon(icon, null) },
        trailingIcon = {
            if (onClick != null) {
                IconButton(onClick = onClick) {
                    Icon(Icons.Default.CalendarMonth, null)
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF2563EB),
            unfocusedBorderColor = Color(0xFFE5E7EB),
            focusedContainerColor = Color(0xFFF9FAFB),
            unfocusedContainerColor = Color(0xFFF9FAFB)
        )
    )
}