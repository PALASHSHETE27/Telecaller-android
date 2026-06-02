
package com.ele.telecallerapp.ui.screens

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CallMade
import androidx.compose.material.icons.filled.CallMissed
import androidx.compose.material.icons.filled.CallReceived
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ele.telecallerapp.utils.CallLogHelper
import com.ele.telecallerapp.utils.CallStats

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CallStatsScreen() {

    val context = LocalContext.current
    val activity = context as Activity

    var stats by remember {
        mutableStateOf<CallStats?>(null)
    }

    // ---------------- PERMISSION ----------------

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { granted ->

            if (granted) {

                val s = CallLogHelper.getCallStats(context)

                Log.d("CALL_STATS", s.toString())

                stats = s
            }
        }

    // ---------------- CHECK PERMISSION ----------------

    LaunchedEffect(Unit) {

        val granted =
            activity.checkSelfPermission(
                android.Manifest.permission.READ_CALL_LOG
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED

        if (granted) {

            stats = CallLogHelper.getCallStats(context)

        } else {

            permissionLauncher.launch(
                android.Manifest.permission.READ_CALL_LOG
            )
        }
    }

    // ---------------- UI ----------------

    Scaffold(

        containerColor = Color(0xFFF7F9FC),

        topBar = {

            TopAppBar(

                title = {

                    Column {

                        Text(
                            "Call Analytics",
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            "Track your call performance",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }

    ) { padding ->

        if (stats == null) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),

                contentAlignment = Alignment.Center
            ) {

                Text(
                    "Grant call permission to view analytics",
                    color = Color.Gray
                )
            }

        } else {

            val s = stats!!

            LazyColumn(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),

                contentPadding = PaddingValues(18.dp),

                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {

                // ---------------- TOP GRADIENT CARD ----------------

                item {

                    Card(

                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                12.dp,
                                RoundedCornerShape(28.dp)
                            ),

                        shape = RoundedCornerShape(28.dp),

                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        )
                    ) {

                        Box(
                            modifier = Modifier
                                .background(
                                    Brush.linearGradient(
                                        listOf(
                                            Color(0xFF4F8CFF),
                                            Color(0xFF7BA5FF)
                                        )
                                    )
                                )
                                .padding(24.dp)
                        ) {

                            Column {

                                Text(
                                    "Total Calls",
                                    color = Color.White.copy(alpha = 0.9f),
                                    fontSize = 15.sp
                                )

                                Spacer(Modifier.height(8.dp))

                                Text(
                                    s.total.toString(),
                                    color = Color.White,
                                    fontSize = 42.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(Modifier.height(14.dp))

                                Text(
                                    "Your overall call activity summary",
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }

                // ---------------- FIRST + LAST CALL ----------------

                item {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {

                        PremiumStatCard(
                            title = "First Call",
                            value = s.firstCallTime,
                            icon = Icons.Default.CallReceived,
                            cardColor = Color(0xFFEAF4FF),
                            iconBg = Color(0xFF2F80ED),
                            modifier = Modifier.weight(1f)
                        )

                        PremiumStatCard(
                            title = "Last Call",
                            value = s.lastCallTime,
                            icon = Icons.Default.CallMade,
                            cardColor = Color(0xFFFFF4E8),
                            iconBg = Color(0xFFFF9800),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // ---------------- SECTION TITLE ----------------

                item {

                    Text(
                        "Performance Overview",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF111827)
                    )
                }

                // ---------------- CONNECTED + TOTAL ----------------

                item {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {

                        PremiumStatCard(
                            title = "Connected",
                            value = (s.incoming + s.outgoing).toString(),
                            icon = Icons.Default.Call,
                            cardColor = Color(0xFFEFFBF3),
                            iconBg = Color(0xFF27AE60),
                            modifier = Modifier.weight(1f)
                        )

                        PremiumStatCard(
                            title = "Missed Calls",
                            value = s.missed.toString(),
                            icon = Icons.Default.CallMissed,
                            cardColor = Color(0xFFFFEEEE),
                            iconBg = Color(0xFFEB5757),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // ---------------- MINI STATS ----------------

                item {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        MiniStatCard(
                            title = "Incoming",
                            value = s.incoming.toString(),
                            icon = Icons.Default.CallReceived,
                            tint = Color(0xFF2F80ED),
                            modifier = Modifier.weight(1f)
                        )

                        MiniStatCard(
                            title = "Outgoing",
                            value = s.outgoing.toString(),
                            icon = Icons.Default.CallMade,
                            tint = Color(0xFF27AE60),
                            modifier = Modifier.weight(1f)
                        )

                        MiniStatCard(
                            title = "Missed",
                            value = s.missed.toString(),
                            icon = Icons.Default.CallMissed,
                            tint = Color(0xFFEB5757),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

// ---------------- PREMIUM CARD ----------------

@Composable
fun PremiumStatCard(
    title: String,
    value: String,
    icon: ImageVector,
    cardColor: Color,
    iconBg: Color,
    modifier: Modifier = Modifier
) {

    Card(

        modifier = modifier
            .height(150.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp)
            ),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),

            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        iconBg,
                        RoundedCornerShape(16.dp)
                    ),

                contentAlignment = Alignment.Center
            ) {

                Icon(
                    icon,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Column {

                Text(
                    title,
                    color = Color.Gray,
                    fontSize = 13.sp
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    value,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF111827)
                )
            }
        }
    }
}

// ---------------- MINI CARD ----------------

@Composable
fun MiniStatCard(
    title: String,
    value: String,
    icon: ImageVector,
    tint: Color,
    modifier: Modifier = Modifier
) {

    Card(

        modifier = modifier
            .height(120.dp)
            .shadow(
                5.dp,
                RoundedCornerShape(20.dp)
            ),

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Icon(
                icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(28.dp)
            )

            Text(
                title,
                fontSize = 12.sp,
                color = Color.Gray
            )

            Text(
                value,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF111827)
            )
        }
    }
}