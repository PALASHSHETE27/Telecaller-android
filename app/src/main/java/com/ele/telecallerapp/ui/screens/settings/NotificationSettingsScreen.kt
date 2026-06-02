
package com.ele.telecallerapp.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ele.telecallerapp.viewmodel.NotificationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationSettingsScreen(
    vm: NotificationViewModel = viewModel()
) {

    var enabled by remember { mutableStateOf(true) }

    Scaffold(

        containerColor = Color(0xFFF5F7FC),

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "Notifications",
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
                                Icons.Default.NotificationsActive,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {

                            Text(
                                text = "Notification Settings",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Manage alerts and updates",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // =====================================================
            // SETTINGS CARD
            // =====================================================

            Card(

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(22.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),

                elevation = CardDefaults.cardElevation(3.dp)

            ) {

                Row(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),

                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Box(

                        modifier = Modifier
                            .size(50.dp)
                            .background(
                                Color(0xFFEAF2FF),
                                CircleShape
                            ),

                        contentAlignment = Alignment.Center

                    ) {

                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = null,
                            tint = Color(0xFF2F80ED)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "Enable Notifications",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Receive donor, lead and campaign alerts",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                    }

                    Switch(
                        checked = enabled,

                        onCheckedChange = {

                            enabled = it
                            vm.updateNotificationStatus(it)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // =====================================================
            // INFO TEXT
            // =====================================================

            Text(
                text = "Turn notifications off if you don't want updates about leads, donors or campaigns.",
                color = Color.Gray,
                fontSize = 13.sp,
                lineHeight = 20.sp
            )
        }
    }
}