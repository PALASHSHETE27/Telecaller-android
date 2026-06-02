

package com.ele.telecallerapp

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ele.telecallerapp.viewmodel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerHeader(
    viewModel: ProfileViewModel,
    onClick: () -> Unit
) {
    val profile by viewModel.profile.collectAsState()
    val loading by viewModel.loading.collectAsState()

    LaunchedEffect(Unit) {
        if (profile == null) {
            viewModel.loadProfile()
        }
    }

    Spacer(Modifier.height(18.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF2F6FB)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = Color(0xFF1E88E5),
                modifier = Modifier.size(60.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = profile?.name?.firstOrNull()?.uppercase() ?: "U",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp
                    )
                }
            }

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (loading) "Loading..." else profile?.name ?: "User",
                    color = Color(0xFF111827),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "View Profile",
                    color = Color(0xFF1976D2),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable {
                        onClick()
                    }
                )
            }
        }
    }
}

@Composable
fun SideDrawer(
    selectedItem: String,
    navController: NavHostController,
    rootNavController: NavHostController,
    drawerScope: CoroutineScope,
    drawerState: DrawerState,
    onItemClick: (String) -> Unit,
    profileViewModel: ProfileViewModel = viewModel()
) {
    var showLogoutDialog by remember {
        mutableStateOf(false)
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = {
                showLogoutDialog = false
            },

            title = {
                Text(
                    "Sign Out",
                    fontWeight = FontWeight.Bold
                )
            },

            text = {
                Text("Do you really want to sign out?")
            },

            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false

                        drawerScope.launch {
                            // Clear token
                            TokenStore.clear(navController.context)

                            // Close drawer
                            drawerState.close()

                            // Navigate using ROOT nav controller
                            rootNavController.navigate("login") {
                                popUpTo(0) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1E88E5)
                    )
                ) {
                    Text("Yes")
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp)
            .background(Color(0xFFF7FAFD))
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {

        DrawerHeader(
            viewModel = profileViewModel
        ) {
            onItemClick("edit_profile")
        }

        Spacer(Modifier.height(18.dp))

        Text(
            text = "MENU",
            color = Color(0xFF7B8794),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 8.dp)
        )

        Spacer(Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {

            DrawerItem(
                "Leads",
                Icons.Default.Group,
                "leads",
                selectedItem,
                onItemClick
            )

            DrawerItem(
                "Campaigns",
                Icons.Default.Campaign,
                "campaigns",
                selectedItem,
                onItemClick
            )

            DrawerItem(
                "Message Templates",
                Icons.Default.Chat,
                "message_templates",
                selectedItem,
                onItemClick
            )

            DrawerItem(
                "Donation Form",
                Icons.Default.VolunteerActivism,
                "donation_form",
                selectedItem,
                onItemClick
            )

            DrawerItem(
                "Prasadam Form",
                Icons.Default.Inventory,
                "prasadam_form",
                selectedItem,
                onItemClick
            )

            DrawerItem(
                "My Donors",
                Icons.Default.Favorite,
                "my_donors",
                selectedItem,
                onItemClick
            )

            Spacer(Modifier.height(4.dp))

            HorizontalDivider(
                color = Color(0xFFD9E4F2)
            )

            Spacer(Modifier.height(4.dp))

            DrawerItem(
                "Settings",
                Icons.Default.Settings,
                "settings",
                selectedItem,
                onItemClick
            )
        }

        Spacer(Modifier.height(6.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .clickable {
                    showLogoutDialog = true
                },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF3D6D6)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 14.dp
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Surface(
                    shape = CircleShape,
                    color = Color(0xFFEFC1C1)
                ) {
                    Icon(
                        Icons.Default.Logout,
                        contentDescription = null,
                        tint = Color(0xFF7F0000),
                        modifier = Modifier.padding(9.dp)
                    )
                }

                Spacer(Modifier.width(14.dp))

                Column {
                    Text(
                        "Sign Out",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF7F0000)
                    )

                    Text(
                        "Logout from account",
                        color = Color(0xFF4B5563),
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))
    }
}

@Composable
fun DrawerItem(
    title: String,
    icon: ImageVector,
    route: String,
    selectedItem: String,
    onItemClick: (String) -> Unit
) {
    val selected = route == selectedItem

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(route)
            },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            if (selected) Color(0xFF1E88E5)
            else Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 11.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                shape = CircleShape,
                color =
                if (selected)
                    Color.White.copy(alpha = 0.22f)
                else
                    Color(0xFFE8F1FB)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint =
                    if (selected) Color.White
                    else Color(0xFF1E88E5),
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(Modifier.width(14.dp))

            Text(
                text = title,
                color =
                if (selected) Color.White
                else Color(0xFF1F2937),
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
        }
    }
}