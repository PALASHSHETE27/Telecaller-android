
package com.ele.telecallerapp.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ele.telecallerapp.viewmodel.DonorViewModel

@Composable
fun MyDonorsScreen(
    navController: NavController,
    vm: DonorViewModel = viewModel()
) {
    val donors by vm.donors.collectAsState()
    var search by remember { mutableStateOf("") }
    val context = androidx.compose.ui.platform.LocalContext.current

    LaunchedEffect(Unit) {
        vm.loadDonors()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F7FB))
            .padding(16.dp)
    ) {

        // ================= SEARCH BAR =================
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(
                            brush = Brush.linearGradient(
                                listOf(Color(0xFF6366F1), Color(0xFF374151))
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                BasicTextField(
                    value = search,
                    onValueChange = {
                        search = it
                        if (it.isBlank()) vm.reset() else vm.search(it)
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        color = Color(0xFF111827),
                        fontSize = 15.sp
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    decorationBox = { innerTextField ->
                        if (search.isEmpty()) {
                            Text(
                                "Search by name or number...",
                                color = Color(0xFF9CA3AF),
                                fontSize = 14.sp
                            )
                        }
                        innerTextField()
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ================= CHIPS =================
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ModernChip("All Donors", Color(0xFF2563EB)) {
                vm.reset()
                search = ""
            }

            Spacer(modifier = Modifier.width(10.dp))

            ModernChip("Sort by Date", Color(0xFF7C3AED)) {
                vm.sortByDate()
                search = ""
            }

            Spacer(modifier = Modifier.width(10.dp))

            ModernChip("High Value", Color(0xFF059669)) {
                vm.highValue()
                search = ""
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ================= DONOR LIST =================
        LazyColumn {
            items(donors) { donor ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            navController.navigate("donor_details/${donor.mobile}")
                        },
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {

                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // ================= AVATAR =================
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .background(
                                    brush = Brush.linearGradient(
                                        listOf(Color(0xFFDBEAFE), Color(0xFFE0F2FE))
                                    ),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                donor.donorName.firstOrNull()?.toString() ?: "D",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        // ================= DETAILS =================
                        Column(modifier = Modifier.weight(1f)) {

                            Text(
                                donor.donorName,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF4C1D95)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                "Last: ${donor.lastDate}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF334155)
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                donor.mobile,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF1E293B)
                            )
                        }

                        // ================= RIGHT SIDE =================
                        Column(horizontalAlignment = Alignment.End) {

                            Text(
                                "₹${donor.totalGiven}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1D4ED8)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            IconButton(
                                onClick = {
                                    val intent = Intent(
                                        Intent.ACTION_DIAL,
                                        Uri.parse("tel:${donor.mobile}")
                                    )
                                    context.startActivity(intent)
                                },
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(Color(0xFF22C55E), CircleShape)
                            ) {
                                Icon(
                                    Icons.Default.Call,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ModernChip(
    text: String,
    color: Color,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(30.dp),
        color = color.copy(alpha = 0.12f),
        border = BorderStroke(1.dp, color.copy(alpha = 0.35f)),
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            color = color,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp
        )
    }
}