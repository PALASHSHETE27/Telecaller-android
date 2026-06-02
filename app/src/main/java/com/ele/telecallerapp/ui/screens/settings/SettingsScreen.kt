
package com.ele.telecallerapp.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ele.telecallerapp.DrawerHeader
import com.ele.telecallerapp.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    appNavController: NavController,
    rootNavController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {

    var showLogoutDialog by remember { mutableStateOf(false) }

    // =====================================================
    // LOGOUT DIALOG
    // =====================================================

    if (showLogoutDialog) {

        AlertDialog(

            onDismissRequest = {
                showLogoutDialog = false
            },

            confirmButton = {

                Button(

                    onClick = {

                        showLogoutDialog = false

                        rootNavController.navigate("login") {
                            popUpTo(0)
                        }
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF5252)
                    )

                ) {

                    Text(
                        "Log Out",
                        color = Color.White
                    )
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {
                        showLogoutDialog = false
                    }

                ) {

                    Text(
                        "Cancel",
                        color = Color.Gray
                    )
                }
            },

            title = {

                Text(
                    "Logout",
                    fontWeight = FontWeight.Bold
                )
            },

            text = {

                Text(
                    "Do you really want to log out?"
                )
            },

            shape = RoundedCornerShape(22.dp),

            containerColor = Color.White
        )
    }

    Scaffold(

        containerColor = Color(0xFFF5F7FC),

        topBar = {

            Spacer(modifier = Modifier.height(0.dp))
        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp, vertical = 16.dp)

        ) {

            // =====================================================
            // PROFILE HEADER
            // =====================================================

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(30.dp))
                    .background(

                        Brush.verticalGradient(

                            colors = listOf(
                                Color(0xFF4A90E2),
                                Color(0xFF6FB1FC)
                            )
                        )
                    )
                    .padding(22.dp)
            ) {

                Column {

                    Text(
                        text = "Profile Settings",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Manage your account and preferences",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    Card(

                        shape = RoundedCornerShape(22.dp),

                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )

                    ) {

                        Row(

                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),

                            verticalAlignment = Alignment.CenterVertically,

                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            DrawerHeader(viewModel = profileViewModel) {

                                appNavController.navigate("edit_profile") {
                                    launchSingleTop = true
                                }
                            }

                            Box(

                                modifier = Modifier
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(Color(0xFFEAF2FF))
                                    .clickable {

                                        appNavController.navigate("edit_profile") {
                                            launchSingleTop = true
                                        }
                                    }
                                    .padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    )

                            ) {

                                Text(
                                    text = "Edit",
                                    color = Color(0xFF2F80ED),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            // =====================================================
            // ACCOUNT
            // =====================================================

            SectionTitle("ACCOUNT")

            SettingsItem(
                title = "Change Password",
                subtitle = "Update your account password",
                icon = Icons.Default.Lock,
                iconBg = Color(0xFFE8F1FF)
            ) {

                rootNavController.navigate("forgot")
            }

            Spacer(modifier = Modifier.height(18.dp))

            // =====================================================
            // PREFERENCES
            // =====================================================

            SectionTitle("PREFERENCES")

            SettingsItem(
                title = "Notifications",
                subtitle = "Manage notification settings",
                icon = Icons.Default.Notifications,
                iconBg = Color(0xFFFFF4E5)
            ) {

                appNavController.navigate("notifications")
            }

            SettingsItem(
                title = "Language & Region",
                subtitle = "Choose app language and region",
                icon = Icons.Default.Language,
                iconBg = Color(0xFFF4E8FF)
            ) {

                appNavController.navigate("language_region")
            }

            Spacer(modifier = Modifier.height(28.dp))

            // =====================================================
            // LOGOUT BUTTON
            // =====================================================

            Button(

                onClick = {
                    showLogoutDialog = true
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),

                shape = RoundedCornerShape(20.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF5A5F)
                )

            ) {

                Icon(
                    Icons.Default.Logout,
                    contentDescription = null,
                    tint = Color.White
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Log Out",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// =========================================================
// SECTION TITLE
// =========================================================

@Composable
fun SectionTitle(title: String) {

    Text(

        text = title,

        color = Color(0xFF7B8AA0),

        fontSize = 12.sp,

        fontWeight = FontWeight.Bold,

        modifier = Modifier.padding(bottom = 12.dp)
    )
}

// =========================================================
// SETTINGS ITEM
// =========================================================

@Composable
fun SettingsItem(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconBg: Color,
    onClick: () -> Unit
) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp)
            .clickable { onClick() },

        shape = RoundedCornerShape(22.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(3.dp)

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {

            Box(

                modifier = Modifier
                    .size(52.dp)
                    .background(iconBg, CircleShape),

                contentAlignment = Alignment.Center

            ) {

                Icon(
                    icon,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}