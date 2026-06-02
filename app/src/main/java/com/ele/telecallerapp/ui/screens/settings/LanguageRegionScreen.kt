
package com.ele.telecallerapp.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageRegionScreen() {

    var language by remember { mutableStateOf("English") }

    Scaffold(

        containerColor = Color(0xFFF5F7FC),

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "Language & Region",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(18.dp)

        ) {

            // =====================================================
            // HEADER CARD
            // =====================================================

            Card(

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(26.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),

                elevation = CardDefaults.cardElevation(4.dp)

            ) {

                Box(

                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF2F80ED),
                                    Color(0xFF56CCF2)
                                )
                            )
                        )
                        .padding(22.dp)

                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(

                            modifier = Modifier
                                .size(58.dp)
                                .background(
                                    Color.White.copy(alpha = 0.18f),
                                    CircleShape
                                ),

                            contentAlignment = Alignment.Center

                        ) {

                            Icon(
                                Icons.Default.Language,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {

                            Text(
                                text = "Language Settings",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Choose your preferred language",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // =====================================================
            // LANGUAGE CARD
            // =====================================================

            Card(

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(22.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),

                elevation = CardDefaults.cardElevation(3.dp)

            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = "App Language",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Select the language used in the app",
                        color = Color.Gray,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    DropdownMenuBox(
                        options = listOf("English"),
                        selected = language,
                        onSelected = { language = it }
                    )
                }
            }
        }
    }
}

@Composable
fun DropdownMenuBox(
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    Box {

        OutlinedButton(

            onClick = { expanded = true },

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(16.dp),

            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color(0xFFF8FAFF)
            )

        ) {

            Row(

                modifier = Modifier.fillMaxWidth(),

                verticalAlignment = Alignment.CenterVertically,

                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                Text(
                    text = selected,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )

                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            options.forEach {

                DropdownMenuItem(

                    text = {

                        Text(
                            text = it,
                            color = Color.Black
                        )
                    },

                    onClick = {

                        onSelected(it)
                        expanded = false
                    }
                )
            }
        }
    }
}