
package com.ele.telecallerapp

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(
    navController: NavController
) {

    val items = listOf(

        BottomNavItem(
            "Dashboard",
            "dashboard",
            Icons.Default.Dashboard
        ),

        BottomNavItem(
            "Leads",
            "leads",
            Icons.Default.People
        ),

        BottomNavItem(
            "Campaigns",
            "campaigns",
            Icons.Default.Campaign
        ),

        BottomNavItem(
            "Call Stats",
            "call_stats",
            Icons.Default.BarChart
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute =
        navBackStackEntry
            ?.destination
            ?.route
            ?.substringBefore("/")

    NavigationBar(

        modifier = Modifier.background(
            Color(0xFFEAF4FF)
        ),

        containerColor = Color.White,

        tonalElevation = 12.dp
    ) {

        items.forEach { item ->

            val selected = currentRoute == item.route

            NavigationBarItem(

                selected = selected,

                onClick = {

                    navController.navigate(item.route) {

                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },

                icon = {

                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },

                label = {
                    Text(item.title)
                },

                colors = NavigationBarItemDefaults.colors(

                    selectedIconColor = Color.White,

                    selectedTextColor = Color(0xFF1565C0),

                    unselectedIconColor = Color(0xFF7B8794),

                    unselectedTextColor = Color(0xFF7B8794),

                    indicatorColor = Color(0xFF42A5F5)
                )
            )
        }
    }
}

data class BottomNavItem(

    val title: String,

    val route: String,

    val icon: androidx.compose.ui.graphics.vector.ImageVector
)