
package com.ele.telecallerapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ele.telecallerapp.viewmodel.DashboardViewModel
import com.ele.telecallerapp.viewmodel.UiActivityItem
import com.ele.telecallerapp.viewmodel.UiDashboardStats

/* ============================= SCREEN ============================= */

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = viewModel()) {

    val stats by viewModel.stats.collectAsState()
    val activities by viewModel.activities.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    DashboardContent(stats, activities.take(5))
}

/* ============================= CONTENT ============================= */

@Composable
fun DashboardContent(
    stats: UiDashboardStats,
    activities: List<UiActivityItem>
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F8FF))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        item {
            InterestedLeadsCard(stats)
        }

        item {
            StatsRow(stats)
        }

        item {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    "Recent Lead Activity",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    "Top 5",
                    color = Color(0xFF1565C0),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        items(activities) {

            val activityDescription =
                when {
                    it.description.contains("message", true) ->
                        "Message sent successfully"

                    it.description.contains("lead", true) ->
                        "Lead created successfully"

                    it.description.contains("delete", true) ->
                        "Lead deleted"

                    it.description.contains("call", true) ->
                        "Call completed"

                    else ->
                        it.description
                }

            val activityIcon =
                when {
                    it.description.contains("message", true) ->
                        Icons.Default.Send

                    it.description.contains("lead", true) ->
                        Icons.Default.PersonAdd

                    it.description.contains("delete", true) ->
                        Icons.Default.Delete

                    else ->
                        Icons.Default.Call
                }

            RecentActivityItem(
                name = it.type,
                time = it.time,
                description = activityDescription,
                icon = activityIcon
            )
        }

        item {
            Spacer(Modifier.height(12.dp))
        }
    }
}

/* ============================= INTERESTED CARD ============================= */

@Composable
fun InterestedLeadsCard(stats: UiDashboardStats) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(185.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF42A5F5),
                            Color(0xFF1565C0)
                        )
                    )
                )
                .padding(22.dp)
        ) {

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(44.dp)
                    .background(
                        Color.White.copy(alpha = 0.18f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column {

                    Text(
                        "HIGH PRIORITY",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        "Interested Leads",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        stats.interestedLeads.toString(),
                        fontSize = 46.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(Modifier.width(14.dp))

                    Surface(
                        shape = RoundedCornerShape(50),
                        color = Color.White.copy(alpha = 0.25f)
                    ) {

                        Row(
                            modifier = Modifier.padding(
                                horizontal = 12.dp,
                                vertical = 6.dp
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                Icons.Default.TrendingUp,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )

                            Spacer(Modifier.width(6.dp))

                            Text(
                                "+${stats.todayIncrease} today",
                                color = Color.White,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

/* ============================= STATS ============================= */

@Composable
fun StatsRow(stats: UiDashboardStats) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        StatCard(
            value = stats.freshLeads.toString(),
            label = "Fresh Leads",
            icon = Icons.Default.Verified,
            iconBg = Color(0xFFC8F7D2),
            iconTint = Color(0xFF1B5E20),
            chipText = "+${stats.todayIncrease}",
            chipBg = Color(0xFFB9F6CA),
            chipTextColor = Color(0xFF1B5E20),
            modifier = Modifier.weight(1f)
        )

        StatCard(
            value = stats.contacted.toString(),
            label = "Contacted",
            icon = Icons.Default.Call,
            iconBg = Color(0xFFD7CCFF),
            iconTint = Color(0xFF4527A0),
            chipText = "Pending",
            chipBg = Color(0xFFE1D5FF),
            chipTextColor = Color(0xFF4527A0),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatCard(
    value: String,
    label: String,
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    chipText: String,
    chipBg: Color,
    chipTextColor: Color,
    modifier: Modifier
) {

    Card(
        modifier,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            Modifier.padding(16.dp)
        ) {

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    Modifier
                        .size(40.dp)
                        .background(iconBg, CircleShape),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        icon,
                        contentDescription = null,
                        tint = iconTint
                    )
                }

                Surface(
                    shape = RoundedCornerShape(50),
                    color = chipBg
                ) {

                    Text(
                        chipText,
                        Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 6.dp
                        ),
                        color = chipTextColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            Text(
                value,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                label,
                fontSize = 14.sp,
                color = Color(0xFF424242)
            )
        }
    }
}

/* ============================= ACTIVITY ============================= */

@Composable
fun RecentActivityItem(
    name: String,
    time: String,
    description: String,
    icon: ImageVector
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {

        Row(
            Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                Modifier
                    .size(48.dp)
                    .background(
                        Color(0xFFDCEBFF),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    icon,
                    contentDescription = null,
                    tint = Color(0xFF1565C0)
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(
                Modifier.weight(1f)
            ) {

                Text(
                    name,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    description,
                    fontSize = 13.sp,
                    color = Color(0xFF424242)
                )
            }

            Text(
                time,
                fontSize = 12.sp,
                color = Color(0xFF424242)
            )
        }
    }
}