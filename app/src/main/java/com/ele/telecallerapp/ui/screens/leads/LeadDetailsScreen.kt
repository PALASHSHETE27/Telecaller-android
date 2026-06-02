
package com.ele.telecallerapp.ui.screens.leads

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ele.telecallerapp.data.repository.LeadRepository
import com.ele.telecallerapp.viewmodel.LeadDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeadDetailsScreen(leadId: String) {

    val context = LocalContext.current

    // ✅ FIXED VIEWMODEL CREATION
    val vm: LeadDetailsViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LeadDetailsViewModel(LeadRepository(context)) as T
            }
        }
    )

    val lead by vm.lead.collectAsState()
    val activities by vm.activities.collectAsState()

    var note by remember { mutableStateOf("") }

    LaunchedEffect(leadId) {
        vm.load(leadId)
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    placeholder = { Text("Add a quick note...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        if (note.isNotBlank()) {
                            vm.addNote(note)
                            note = ""
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                ) {
                    Icon(Icons.Default.Send, null, tint = Color.White)
                }
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF7F9FC))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                if (!lead?.imageUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = lead!!.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = lead?.name?.firstOrNull()?.uppercase() ?: "?",
                            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    lead?.name ?: "",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    lead?.phone ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AssistChip(onClick = {}, label = { Text(lead?.company ?: "Lead") })

                    AssistChip(
                        onClick = {},
                        label = { Text(lead?.status ?: "") },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = statusColor(lead?.status ?: "").copy(alpha = 0.15f),
                            labelColor = statusColor(lead?.status ?: "")
                        )
                    )
                }

                Spacer(Modifier.height(20.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                    ActionButton("Call", Icons.Default.Call, Color(0xFF2196F3)) {
                        context.startActivity(
                            Intent(Intent.ACTION_DIAL, Uri.parse("tel:${lead?.phone}"))
                        )
                        vm.logCall()
                    }

                    ActionButton("WhatsApp", Icons.Default.Message, Color(0xFF25D366)) {
                        context.startActivity(
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/${lead?.phone}"))
                        )
                        vm.logWhatsApp()
                    }

                    ActionButton("SMS", Icons.Default.Message, Color(0xFF9E9E9E)) {
                        context.startActivity(
                            Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${lead?.phone}"))
                        )
                        vm.logSms()
                    }
                }

                Spacer(Modifier.height(28.dp))
                Text("Activity History", fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(12.dp))
            }

            items(activities) { act ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(act.type, fontWeight = FontWeight.SemiBold)
                        if (!act.description.isNullOrEmpty()) {
                            Text(
                                act.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            item { Spacer(Modifier.height(90.dp)) }
        }
    }
}

/* ------------------ UI HELPERS ------------------ */

@Composable
fun ActionButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = color)
        }
        Spacer(Modifier.height(6.dp))
        Text(text, style = MaterialTheme.typography.labelSmall)
    }
}

fun statusColor(status: String): Color = when (status.lowercase()) {
    "fresh" -> Color(0xFF4CAF50)
    "contacted" -> Color(0xFF2196F3)
    "interested" -> Color(0xFFFF9800)
    "callback" -> Color(0xFF9C27B0)
    "closed" -> Color(0xFFF44336)
    else -> Color.Gray
}
