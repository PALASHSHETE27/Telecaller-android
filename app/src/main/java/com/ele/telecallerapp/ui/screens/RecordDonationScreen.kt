
package com.ele.telecallerapp.ui.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ele.telecallerapp.network.DonationDto
import com.ele.telecallerapp.session.UserSession
import com.ele.telecallerapp.viewmodel.DonationViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordDonationScreen(
    vm: DonationViewModel = viewModel()
) {

    val context = LocalContext.current
    val teleCaller = UserSession.name

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var date by remember { mutableStateOf("") }
    var donorName by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    val donorTypes = listOf(
        "One-time Donor",
        "Regular Donor",
        "VIP Donor"
    )

    var donorType by remember { mutableStateOf(donorTypes[0]) }
    var donorExpanded by remember { mutableStateOf(false) }

    val paymentModes = listOf(
        "UPI",
        "Cash",
        "Card",
        "Net Banking"
    )

    var paymentMode by remember { mutableStateOf(paymentModes[0]) }
    var paymentExpanded by remember { mutableStateOf(false) }

    var amount by remember { mutableStateOf("") }
    var paymentType by remember { mutableStateOf("General") }

    // DATE PICKER
    fun openDatePicker() {

        val calendar = Calendar.getInstance()

        DatePickerDialog(
            context,
            { _, y, m, d ->

                val selected = Calendar.getInstance()
                selected.set(y, m, d)

                date = SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale.getDefault()
                ).format(selected.time)

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)

        ).show()
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        containerColor = Color(0xFFF8FBFF)
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFDCEEFF),
                            Color(0xFFEFF6FF),
                            Color.White
                        )
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(18.dp)
            ) {

                Spacer(modifier = Modifier.height(6.dp))

                // TOP HEADER
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(58.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF2563EB),
                                        Color(0xFF1D4ED8)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            Icons.Default.VolunteerActivism,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {

                        Text(
                            text = "Donation Form",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF0F172A)
                        )

                        Text(
                            text = "Manage donor contributions beautifully",
                            fontSize = 14.sp,
                            color = Color(0xFF475569)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                // MAIN CARD
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(38.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE0F2FE)
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 20.dp
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(18.dp)
                    ) {

                        // SECTION TITLE
                        Text(
                            text = "Donor Details",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E3A8A)
                        )

                        // DATE + TELECALLER
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            OutlinedTextField(
                                value = date,
                                onValueChange = {},
                                readOnly = true,
                                label = {
                                    Text("Date")
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(24.dp),
                                colors = textFieldColors(),
                                trailingIcon = {

                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Color(0xFF2563EB)),
                                        contentAlignment = Alignment.Center
                                    ) {

                                        IconButton(
                                            onClick = {
                                                openDatePicker()
                                            }
                                        ) {

                                            Icon(
                                                Icons.Default.DateRange,
                                                contentDescription = null,
                                                tint = Color.White
                                            )
                                        }
                                    }
                                }
                            )

                            OutlinedTextField(
                                value = teleCaller,
                                onValueChange = {},
                                enabled = false,
                                label = {
                                    Text("Tele Caller")
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(24.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    disabledContainerColor = Color.White,
                                    disabledBorderColor = Color(0xFFBFDBFE)
                                )
                            )
                        }

                        // DONOR NAME
                        OutlinedTextField(
                            value = donorName,
                            onValueChange = {
                                donorName = it
                            },
                            label = {
                                Text("Donor Name")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            colors = textFieldColors()
                        )

                        // MOBILE + LOCATION
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            OutlinedTextField(
                                value = mobile,
                                onValueChange = {

                                    if (it.length <= 10) {
                                        mobile = it.filter { ch ->
                                            ch.isDigit()
                                        }
                                    }
                                },
                                label = {
                                    Text("Mob Number")
                                },

                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(24.dp),
                                colors = textFieldColors()
                            )

                            OutlinedTextField(
                                value = location,
                                onValueChange = {
                                    location = it
                                },
                                label = {
                                    Text("Location")
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(24.dp),
                                colors = textFieldColors()
                            )
                        }

                        // DONOR TYPE DROPDOWN
                        ExposedDropdownMenuBox(
                            expanded = donorExpanded,
                            onExpandedChange = {
                                donorExpanded = !donorExpanded
                            }
                        ) {

                            OutlinedTextField(
                                value = donorType,
                                onValueChange = {},
                                readOnly = true,
                                label = {
                                    Text("Donor Type")
                                },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(24.dp),
                                colors = textFieldColors(),
                                trailingIcon = {

                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Color(0xFF2563EB)),
                                        contentAlignment = Alignment.Center
                                    ) {

                                        Icon(
                                            Icons.Default.KeyboardArrowDown,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                                }
                            )

                            ExposedDropdownMenu(
                                expanded = donorExpanded,
                                onDismissRequest = {
                                    donorExpanded = false
                                },
                                modifier = Modifier
                                    .background(Color.White)
                                    .border(
                                        1.dp,
                                        Color(0xFFBFDBFE),
                                        RoundedCornerShape(18.dp)
                                    )
                            ) {

                                donorTypes.forEach {

                                    DropdownMenuItem(
                                        text = {

                                            Text(
                                                text = it,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color(0xFF1E3A8A)
                                            )
                                        },
                                        onClick = {

                                            donorType = it
                                            donorExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        HorizontalDivider(
                            thickness = 1.dp,
                            color = Color(0xFF93C5FD)
                        )

                        // PAYMENT TITLE
                        Text(
                            text = "Payment Information",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E3A8A)
                        )

                        // AMOUNT
                        OutlinedTextField(
                            value = amount,
                            onValueChange = {
                                amount = it
                            },
                            label = {
                                Text("Donation Amount")
                            },
                            leadingIcon = {

                                Text(
                                    text = "₹",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2563EB),
                                    fontSize = 18.sp
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            colors = textFieldColors()
                        )

                        // PAYMENT TYPE
                        OutlinedTextField(
                            value = paymentType,
                            onValueChange = {
                                paymentType = it
                            },
                            label = {
                                Text("Payment Type")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            colors = textFieldColors()
                        )

                        // PAYMENT MODE
                        ExposedDropdownMenuBox(
                            expanded = paymentExpanded,
                            onExpandedChange = {
                                paymentExpanded = !paymentExpanded
                            }
                        ) {

                            OutlinedTextField(
                                value = paymentMode,
                                onValueChange = {},
                                readOnly = true,
                                label = {
                                    Text("Payment Mode")
                                },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(24.dp),
                                colors = textFieldColors(),
                                trailingIcon = {

                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Color(0xFF2563EB)),
                                        contentAlignment = Alignment.Center
                                    ) {

                                        Icon(
                                            Icons.Default.KeyboardArrowDown,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                                }
                            )

                            ExposedDropdownMenu(
                                expanded = paymentExpanded,
                                onDismissRequest = {
                                    paymentExpanded = false
                                },
                                modifier = Modifier
                                    .background(Color.White)
                                    .border(
                                        1.dp,
                                        Color(0xFFBFDBFE),
                                        RoundedCornerShape(18.dp)
                                    )
                            ) {

                                paymentModes.forEach {

                                    DropdownMenuItem(
                                        text = {

                                            Text(
                                                text = it,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color(0xFF1E3A8A)
                                            )
                                        },
                                        onClick = {

                                            paymentMode = it
                                            paymentExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // SAVE BUTTON
                        Button(
                            onClick = {

                                vm.submitDonation(
                                    DonationDto(
                                        date = date,
                                        donorName = donorName,
                                        mobile = "+91$mobile",
                                        location = location,
                                        donorType = donorType,
                                        amount = amount.toDoubleOrNull() ?: 0.0,
                                        paymentType = paymentType,
                                        paymentMode = paymentMode
                                    )
                                )

                                scope.launch {

                                    snackbarHostState.showSnackbar(
                                        "Donation saved successfully ✅"
                                    )
                                }

                                donorName = ""
                                mobile = ""
                                location = ""
                                amount = ""
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(66.dp),
                            shape = RoundedCornerShape(26.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 10.dp
                            ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1D4ED8)
                            )
                        ) {

                            Text(
                                text = "Save Donation",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Color(0xFF2563EB),
    unfocusedBorderColor = Color(0xFFBFDBFE),
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    cursorColor = Color(0xFF2563EB),
    focusedLabelColor = Color(0xFF2563EB)
)