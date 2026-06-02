
package com.ele.telecallerapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ele.telecallerapp.network.MessageTemplateDto

@Composable
fun MessageTemplateCard(
    template: MessageTemplateDto,
    selected: Boolean,
    onClick: () -> Unit
) {

    // ======================================================
// LIGHT CATEGORY COLORS (UPDATED)
// ======================================================
    val categoryColor = when (template.category.lowercase()) {

        "authentication" -> Color(0xFFFFEBEE) // light red
        "marketing" -> Color(0xFFFFF3E0)       // light orange
        "utility" -> Color(0xFFF3E5F5)         // light purple
        else -> Color(0xFFF3F4F6)              // light gray
    }

    val categoryTextColor = when (template.category.lowercase()) {

        "authentication" -> Color(0xFFD32F2F) // red
        "marketing" -> Color(0xFFEF6C00)       // orange
        "utility" -> Color(0xFF7B1FA2)         // purple
        else -> Color(0xFF616161)              // gray
    }

    // ======================================================
    // SELECTION BORDER ONLY (BLUE)
    // ======================================================
    val borderColor = if (selected)
        Color(0xFF2196F3)
    else
        Color(0xFFE0E0E0)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 4.dp)
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (selected) 5.dp else 2.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {

            // LEFT SOFT CATEGORY BAR
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(60.dp)
                    .background(
                        categoryTextColor.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(50)
                    )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {

                // TOP ROW
                Row(verticalAlignment = Alignment.CenterVertically) {

                    AssistChip(
                        onClick = {},
                        label = {
                            Text(
                                text = if (template.isNew) "NEW" else "ACTIVE",
                                fontSize = 11.sp
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = if (template.isNew)
                                Color(0xFFE3F2FD)
                            else
                                Color(0xFFE8F5E9),
                            labelColor = if (template.isNew)
                                Color(0xFF1976D2)
                            else
                                Color(0xFF2E7D32)
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // CATEGORY TAG (LIGHT PASTEL)
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = categoryColor
                    ) {
                        Text(
                            text = template.category.uppercase(),
                            modifier = Modifier.padding(
                                horizontal = 10.dp,
                                vertical = 4.dp
                            ),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = categoryTextColor
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    RadioButton(
                        selected = selected,
                        onClick = onClick,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xFF2196F3)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = template.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1A1A1A)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = template.content,
                    fontSize = 13.sp,
                    color = Color(0xFF6B7280),
                    maxLines = 2
                )
            }
        }
    }
}