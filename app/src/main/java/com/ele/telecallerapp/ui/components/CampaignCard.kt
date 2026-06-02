
package com.ele.telecallerapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ele.telecallerapp.network.CampaignDto

@Composable
fun CampaignCard(
    campaign: CampaignDto,
    onDelete: () -> Unit
) {

    val (icon, color) = statusIcon(campaign.status)

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    // ----- SAFE PROGRESS CALCULATION -----

    val total = campaign.totalLeads

    val called = campaign.calledLeads

    val progress = if (total > 0) {

        (called.toFloat() / total.toFloat())
            .coerceIn(0f, 1f)

    } else {

        0f
    }

    // ---------------- DELETE DIALOG ----------------

    if (showDeleteDialog) {

        AlertDialog(

            onDismissRequest = {
                showDeleteDialog = false
            },

            title = {
                Text("Delete Campaign")
            },

            text = {
                Text("Are you sure you want to delete this campaign?")
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        onDelete()

                        showDeleteDialog = false
                    }

                ) {

                    Text(
                        "Yes",
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

                    Text("No")
                }
            }
        )
    }

    // ---------------- CAMPAIGN CARD ----------------

    Card(

        shape = RoundedCornerShape(18.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),

        border = BorderStroke(
            1.dp,
            Color(0xFFE9EEF5)
        ),

        modifier = Modifier.fillMaxWidth()

    ) {

        Column(

            modifier = Modifier.padding(16.dp)

        ) {

            // ---------------- HEADER ----------------

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceBetween,

                verticalAlignment = Alignment.CenterVertically

            ) {

                Row(

                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Icon(

                        imageVector = icon,

                        contentDescription = null,

                        tint = color
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(

                        text = campaign.title,

                        fontWeight = FontWeight.SemiBold,

                        style = MaterialTheme.typography.titleMedium,

                        color = Color(0xFF111827)
                    )
                }

                IconButton(

                    onClick = {
                        showDeleteDialog = true
                    }

                ) {

                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }

            // ---------------- AUDIENCE ----------------

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(

                text = "Audience: ${campaign.audience}",

                color = Color(0xFF6B7280),

                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // ---------------- PROGRESS TEXT ----------------

            Text(

                text = "Progress: $called / $total",

                color = Color(0xFF374151),

                style = MaterialTheme.typography.bodyMedium,

                fontWeight = FontWeight.Medium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // ---------------- PROGRESS BAR ----------------

            LinearProgressIndicator(

                progress = progress,

                color = color,

                trackColor = Color(0xFFE5E7EB),

                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)

            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // ---------------- DATES ----------------

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                Text(

                    text = "Started: ${campaign.startDate}",

                    color = Color(0xFF6B7280),

                    style = MaterialTheme.typography.bodySmall
                )

                Text(

                    text = "Due: ${campaign.dueDate}",

                    color = Color(0xFF6B7280),

                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

// ---------------- STATUS ICONS ----------------

fun statusIcon(status: String): Pair<ImageVector, Color> =

    when (status) {

        "Active" -> Icons.Default.Campaign to Color(0xFF2ECC71)

        "Paused" -> Icons.Default.PauseCircle to Color(0xFFF2994A)

        "Draft" -> Icons.Default.Edit to Color.Gray

        "Completed" -> Icons.Default.CheckCircle to Color(0xFF27AE60)

        else -> Icons.Default.Help to Color.Gray
    }