
package com.ele.telecallerapp.ui.screens.campaign

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ele.telecallerapp.viewmodel.CampaignViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCampaignScreen(nav: NavController) {

    // =====================================================
    // VIEWMODEL
    // =====================================================

    val vm: CampaignViewModel = viewModel()

    // =====================================================
    // STATES
    // =====================================================

    var title by remember { mutableStateOf("") }

    var audience by remember { mutableStateOf("") }

    var total by remember { mutableStateOf("") }

    var called by remember { mutableStateOf("") }

    var start by remember { mutableStateOf("") }

    var due by remember { mutableStateOf("") }

    var status by remember { mutableStateOf("Active") }

    var expanded by remember { mutableStateOf(false) }

    val statusOptions = listOf(
        "Active",
        "Draft",
        "Paused",
        "Completed"
    )

    // =====================================================
    // PROGRESS
    // =====================================================

    val totalInt = total.toIntOrNull() ?: 0

    val calledInt = called.toIntOrNull() ?: 0

    val progress = if (totalInt > 0) {

        (calledInt.toFloat() / totalInt.toFloat())
            .coerceIn(0f, 1f)

    } else {
        0f
    }

    // =====================================================
    // SCREEN
    // =====================================================

    Scaffold(

        containerColor = Color(0xFFF7F9FC)

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF7F9FC))
                .padding(padding)
                .padding(horizontal = 18.dp)
                .verticalScroll(rememberScrollState())

        ) {

            Spacer(modifier = Modifier.height(6.dp))

            // =====================================================
            // HEADER
            // =====================================================

            Column(

                modifier = Modifier.fillMaxWidth(),

                horizontalAlignment =
                Alignment.CenterHorizontally

            ) {

                Text(

                    text = "Create Campaign",

                    style = MaterialTheme
                        .typography
                        .headlineMedium,

                    fontWeight = FontWeight.Bold,

                    color = Color(0xFF111827)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(

                    text = "Create and manage outreach campaigns",

                    style = MaterialTheme
                        .typography
                        .bodyMedium,

                    color = Color(0xFF6B7280)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // =====================================================
            // FORM CARD
            // =====================================================

            Card(

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(24.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFEFF6FF)
                ),

                border = BorderStroke(
                    1.dp,
                    Color(0xFFD6E8FF)
                ),

                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )

            ) {

                Column(

                    modifier = Modifier.padding(18.dp),

                    verticalArrangement =
                    Arrangement.spacedBy(16.dp)

                ) {

                    // =====================================================
                    // TITLE
                    // =====================================================

                    ModernField(
                        value = title,
                        onValueChange = { title = it },
                        label = "Campaign Title"
                    )

                    // =====================================================
                    // AUDIENCE
                    // =====================================================

                    ModernField(
                        value = audience,
                        onValueChange = { audience = it },
                        label = "Audience"
                    )

                    // =====================================================
                    // TOTAL LEADS
                    // =====================================================

                    ModernField(
                        value = total,
                        onValueChange = { total = it },
                        label = "Total Leads"
                    )

                    // =====================================================
                    // CALLED LEADS
                    // =====================================================

                    ModernField(
                        value = called,
                        onValueChange = { called = it },
                        label = "Called Leads"
                    )

                    // =====================================================
                    // START DATE
                    // =====================================================

                    ModernField(
                        value = start,
                        onValueChange = { start = it },
                        label = "Start Date"
                    )

                    // =====================================================
                    // DUE DATE
                    // =====================================================

                    ModernField(
                        value = due,
                        onValueChange = { due = it },
                        label = "Due Date"
                    )

                    // =====================================================
                    // STATUS DROPDOWN
                    // =====================================================

                    ExposedDropdownMenuBox(

                        expanded = expanded,

                        onExpandedChange = {
                            expanded = !expanded
                        }

                    ) {

                        OutlinedTextField(

                            value = status,

                            onValueChange = {},

                            readOnly = true,

                            label = {
                                Text("Campaign Status")
                            },

                            trailingIcon = {

                                ExposedDropdownMenuDefaults
                                    .TrailingIcon(
                                        expanded = expanded
                                    )
                            },

                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),

                            shape = RoundedCornerShape(18.dp),

                            colors =
                            OutlinedTextFieldDefaults.colors(

                                focusedBorderColor =
                                MaterialTheme
                                    .colorScheme
                                    .primary,

                                unfocusedBorderColor =
                                Color(0xFFD1D5DB),

                                focusedContainerColor =
                                Color.White,

                                unfocusedContainerColor =
                                Color.White
                            )
                        )

                        ExposedDropdownMenu(

                            expanded = expanded,

                            onDismissRequest = {
                                expanded = false
                            }

                        ) {

                            statusOptions.forEach { option ->

                                DropdownMenuItem(

                                    text = {

                                        Text(
                                            text = option
                                        )
                                    },

                                    onClick = {

                                        status = option

                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // =====================================================
            // PROGRESS CARD
            // =====================================================

            if (totalInt > 0) {

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(20.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),

                    border = BorderStroke(
                        1.dp,
                        Color(0xFFE5E7EB)
                    )

                ) {

                    Column(

                        modifier = Modifier.padding(18.dp)

                    ) {

                        Row(

                            modifier = Modifier.fillMaxWidth(),

                            horizontalArrangement =
                            Arrangement.SpaceBetween

                        ) {

                            Text(

                                text = "Campaign Progress",

                                fontWeight =
                                FontWeight.SemiBold,

                                color = Color(0xFF111827)
                            )

                            Text(

                                text =
                                "$calledInt / $totalInt",

                                fontWeight =
                                FontWeight.Bold,

                                color = MaterialTheme
                                    .colorScheme
                                    .primary
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        LinearProgressIndicator(

                            progress = { progress },

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp),

                            color = MaterialTheme
                                .colorScheme
                                .primary,

                            trackColor = Color(0xFFE5E7EB),

                            strokeCap = StrokeCap.Round
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // =====================================================
            // CREATE BUTTON
            // =====================================================

            Button(

                onClick = {

                    vm.create(
                        title,
                        audience,
                        totalInt,
                        calledInt,
                        start,
                        due,
                        status
                    )

                    nav.navigate("campaigns") {

                        popUpTo("create_campaign") {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),

                shape = RoundedCornerShape(18.dp)

            ) {

                Text(

                    text = "Create Campaign",

                    style = MaterialTheme
                        .typography
                        .titleMedium,

                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// =====================================================
// MODERN FIELD
// =====================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {

    OutlinedTextField(

        value = value,

        onValueChange = onValueChange,

        label = {
            Text(label)
        },

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(18.dp),

        colors = OutlinedTextFieldDefaults.colors(

            focusedBorderColor =
            MaterialTheme.colorScheme.primary,

            unfocusedBorderColor =
            Color(0xFFD1D5DB),

            focusedContainerColor = Color.White,

            unfocusedContainerColor = Color.White
        )
    )
}
