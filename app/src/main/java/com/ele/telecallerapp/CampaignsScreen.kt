
package com.ele.telecallerapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ele.telecallerapp.ui.components.CampaignCard
import com.ele.telecallerapp.viewmodel.CampaignViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignsScreen(
    navController: NavController
) {

    val vm: CampaignViewModel = viewModel()

    val campaigns by vm.campaigns.collectAsState()

    var searchQuery by remember {
        mutableStateOf("")
    }

    var selectedStatus by remember {
        mutableStateOf("All")
    }

    val statusFilters = listOf(
        "All",
        "Active",
        "Draft",
        "Paused",
        "Completed"
    )

    // ---------------- FILTER LOGIC ----------------

    val filteredCampaigns = campaigns.filter { campaign ->

        val matchesSearch =
            campaign.title.contains(searchQuery, true) ||
                    campaign.audience.contains(searchQuery, true)

        val matchesStatus =
            selectedStatus == "All" ||
                    campaign.status == selectedStatus

        matchesSearch && matchesStatus
    }

    // ---------------- UI ----------------

    Scaffold(

        containerColor = Color.White,

        floatingActionButton = {

            FloatingActionButton(
                onClick = {
                    navController.navigate("create_campaign")
                },

                shape = CircleShape,

                containerColor = MaterialTheme.colorScheme.primary
            ) {

                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(horizontal = 18.dp)
        ) {

            Spacer(Modifier.height(18.dp))

            // ---------------- CLASSY SEARCH BAR ----------------

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(22.dp),
                        ambientColor = Color(0x14000000)
                    ),

                shape = RoundedCornerShape(22.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),

                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    Color(0xFFE6ECF5)
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 18.dp,
                            vertical = 16.dp
                        ),

                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.primaryContainer
                                    )
                                ),
                                CircleShape
                            ),

                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    Spacer(Modifier.width(14.dp))

                    BasicTextField(
                        value = searchQuery,

                        onValueChange = {
                            searchQuery = it
                        },

                        singleLine = true,

                        textStyle = TextStyle(
                            color = Color(0xFF111827),
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            fontWeight = FontWeight.Medium
                        ),

                        modifier = Modifier.fillMaxWidth(),

                        decorationBox = { innerTextField ->

                            if (searchQuery.isEmpty()) {

                                Text(
                                    text = "Search campaigns...",
                                    color = Color(0xFF9CA3AF),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            innerTextField()
                        }
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            // ---------------- FILTER CHIPS ----------------

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),

                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                statusFilters.forEach { status ->

                    FilterChip(

                        selected = selectedStatus == status,

                        onClick = {
                            selectedStatus = status
                        },

                        label = {
                            Text(
                                text = status,
                                fontWeight = FontWeight.SemiBold
                            )
                        },

                        shape = RoundedCornerShape(14.dp),

                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White,
                            containerColor = Color(0xFFF4F6FA),
                            labelColor = Color(0xFF374151)
                        ),

                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = selectedStatus == status,
                            borderColor = Color.Transparent,
                            selectedBorderColor = Color.Transparent
                        )
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // ---------------- CAMPAIGN LIST ----------------

            LazyColumn(
                modifier = Modifier.fillMaxSize(),

                verticalArrangement = Arrangement.spacedBy(16.dp),

                contentPadding = PaddingValues(
                    bottom = 90.dp
                )
            ) {

                items(filteredCampaigns) { campaign ->

                    CampaignCard(
                        campaign = campaign,

                        onDelete = {
                            vm.delete(campaign._id)
                        }
                    )
                }
            }
        }
    }
}