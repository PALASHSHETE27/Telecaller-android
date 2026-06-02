
package com.ele.telecallerapp.ui.screens.leads

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ele.telecallerapp.ui.components.LeadCard
import com.ele.telecallerapp.viewmodel.LeadsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeadsScreen(
    onOpen: (String) -> Unit,
    onAdd: () -> Unit,
    onMessageClick: (String) -> Unit
) {

    val vm: LeadsViewModel = viewModel()

    val leads by vm.leads.collectAsState()

    var search by remember {
        mutableStateOf("")
    }

    var selectedStatus by remember {
        mutableStateOf("All")
    }

    LaunchedEffect(search, selectedStatus) {

        vm.refresh(
            status = if (selectedStatus == "All") null else selectedStatus,
            search = if (search.isBlank()) null else search
        )
    }

    Scaffold(

        containerColor = Color(0xFFF7F8FC),

        floatingActionButton = {

            FloatingActionButton(

                onClick = onAdd,

                shape = CircleShape,

                containerColor = MaterialTheme.colorScheme.primary,

                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 8.dp
                )

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
                .background(Color(0xFFF7F8FC))
                .padding(top = padding.calculateTopPadding())
                .padding(horizontal = 16.dp)

        ) {

            Spacer(modifier = Modifier.height(12.dp))

            // ---------------- SEARCH BAR ----------------

            Card(
                modifier = Modifier
                    .fillMaxWidth(),

                shape = RoundedCornerShape(22.dp),

                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
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
                                brush = Brush.linearGradient(
                                    listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.primaryContainer
                                    )
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

                    Spacer(modifier = Modifier.width(14.dp))

                    BasicTextField(

                        value = search,

                        onValueChange = {
                            search = it
                        },

                        singleLine = true,

                        textStyle = TextStyle(
                            color = Color(0xFF111827),
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        ),

                        modifier = Modifier.fillMaxWidth(),

                        decorationBox = { innerTextField ->

                            if (search.isEmpty()) {

                                Text(
                                    text = "Search by name or number...",
                                    color = Color(0xFF9CA3AF),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            innerTextField()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ---------------- FILTER CHIPS ----------------

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),

                horizontalArrangement = Arrangement.spacedBy(8.dp)

            ) {

                listOf(
                    "All",
                    "Fresh",
                    "Contacted",
                    "Interested"
                ).forEach { status ->

                    FilterChip(

                        selected = selectedStatus == status,

                        onClick = {
                            selectedStatus = status
                        },

                        label = {
                            Text(status)
                        },

                        shape = RoundedCornerShape(14.dp),

                        colors = FilterChipDefaults.filterChipColors(

                            selectedContainerColor = MaterialTheme.colorScheme.primary,

                            selectedLabelColor = Color.White,

                            containerColor = Color.White,

                            labelColor = Color.DarkGray
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

            Spacer(modifier = Modifier.height(18.dp))

            // ---------------- EMPTY STATE ----------------

            AnimatedVisibility(

                visible = leads.isEmpty(),

                enter = fadeIn() + slideInVertically(),

                exit = fadeOut() + slideOutVertically()

            ) {

                Box(

                    modifier = Modifier.fillMaxSize(),

                    contentAlignment = Alignment.Center

                ) {

                    Column(

                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {

                        Text(
                            text = "No Leads Found",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.DarkGray
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Try adding a new lead",
                            color = Color.Gray
                        )
                    }
                }
            }

            // ---------------- LEADS LIST ----------------

            AnimatedVisibility(

                visible = leads.isNotEmpty(),

                enter = fadeIn() + slideInVertically(),

                exit = fadeOut()

            ) {

                LazyColumn(

                    modifier = Modifier.fillMaxSize(),

                    verticalArrangement = Arrangement.spacedBy(14.dp),

                    contentPadding = PaddingValues(
                        bottom = 100.dp
                    )

                ) {

                    items(
                        items = leads,
                        key = { it.id }
                    ) { lead ->

                        LeadCard(

                            lead = lead,

                            onClick = {
                                onOpen(lead.id)
                            },

                            onMessageClick = { phone ->
                                onMessageClick(phone)
                            },

                            onDelete = { id ->

                                vm.deleteLead(

                                    id = id,

                                    status = if (selectedStatus == "All") {
                                        null
                                    } else {
                                        selectedStatus
                                    },

                                    search = if (search.isBlank()) {
                                        null
                                    } else {
                                        search
                                    }
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}