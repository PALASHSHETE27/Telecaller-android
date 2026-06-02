

package com.ele.telecallerapp.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ele.telecallerapp.viewmodel.UiLead
import java.time.Instant
import java.time.temporal.ChronoUnit

@Composable
fun LeadCard(
    lead: UiLead,
    onClick: () -> Unit,
    onMessageClick: (String) -> Unit,
    onDelete: (String) -> Unit
) {

    val context = LocalContext.current

    var showMenu by remember {
        mutableStateOf(false)
    }

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    // ---------------- DELETE DIALOG ----------------

    if (showDeleteDialog) {

        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
            },

            title = {
                Text("Delete Lead")
            },

            text = {
                Text("Are you sure you want to delete this lead?")
            },

            confirmButton = {

                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDelete(lead.id)
                    }
                ) {
                    Text(
                        "Delete",
                        color = Color.Red
                    )
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    // ---------------- CARD ----------------

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },

        shape = RoundedCornerShape(20.dp),

        elevation = CardDefaults.cardElevation(2.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Box {

            // ---------------- THREE DOT MENU ----------------

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 6.dp, end = 6.dp)
            ) {

                IconButton(
                    onClick = {
                        showMenu = true
                    }
                ) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = null
                    )
                }

                DropdownMenu(
                    expanded = showMenu,

                    onDismissRequest = {
                        showMenu = false
                    }
                ) {

                    DropdownMenuItem(

                        text = {
                            Text("Delete Lead")
                        },

                        leadingIcon = {

                            Icon(
                                Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.Red
                            )
                        },

                        onClick = {
                            showMenu = false
                            showDeleteDialog = true
                        }
                    )
                }
            }

            // ---------------- MAIN CONTENT ----------------

            Row(
                modifier = Modifier.padding(16.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {

                // ---------------- AVATAR ----------------

                if (!lead.imageUrl.isNullOrEmpty()) {

                    AsyncImage(
                        model = lead.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,

                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                    )

                } else {

                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE3EBFF)),

                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            lead.name.firstOrNull()?.uppercase() ?: "?",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(Modifier.width(12.dp))

                // ---------------- DETAILS ----------------

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        lead.name,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        lead.company ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        AssistChip(
                            onClick = {},

                            label = {
                                Text(lead.status)
                            },

                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = statusColor(lead.status).copy(alpha = 0.15f),
                                labelColor = statusColor(lead.status)
                            )
                        )

                        Spacer(Modifier.width(6.dp))

                        Text(
                            "• ${getTimeAgo(lead.createdAt)}",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                    }
                }

                // ---------------- ACTION BUTTONS ----------------

                Row(
                    modifier = Modifier
                        .padding(top = 36.dp), // moved more downward
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // MESSAGE BUTTON

                    IconButton(
                        onClick = {
                            onMessageClick(lead.phone)
                        }
                    ) {

                        Icon(
                            Icons.Default.Message,
                            contentDescription = null,
                            tint = Color(0xFF25D366)
                        )
                    }

                    // CALL BUTTON

                    IconButton(
                        onClick = {

                            context.startActivity(
                                Intent(
                                    Intent.ACTION_DIAL,
                                    Uri.parse("tel:${lead.phone}")
                                )
                            )
                        }
                    ) {

                        Icon(
                            Icons.Default.Call,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

/* ---------------- HELPERS ---------------- */

fun statusColor(status: String): Color = when (status) {

    "Fresh" -> Color(0xFF2196F3)

    "Contacted" -> Color(0xFF4CAF50)

    "Interested" -> Color(0xFFFF9800)

    else -> Color.Gray
}

fun getTimeAgo(iso: String): String {

    val time = Instant.parse(iso)
    val now = Instant.now()

    val minutes = ChronoUnit.MINUTES.between(time, now)
    val hours = ChronoUnit.HOURS.between(time, now)
    val days = ChronoUnit.DAYS.between(time, now)

    return when {

        minutes < 1 -> "Just now"

        minutes < 60 -> "$minutes min ago"

        hours < 24 -> "$hours hours ago"

        days == 1L -> "Yesterday"

        else -> "$days days ago"
    }
}