
package com.ele.telecallerapp.ui.screens.message

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ele.telecallerapp.network.MessageTemplateDto
import com.ele.telecallerapp.viewmodel.MessageTemplateViewModel

@Composable
fun EditMessageTemplateScreen(
    template: MessageTemplateDto,
    vm: MessageTemplateViewModel,
    phone: String
) {
    var content by remember { mutableStateOf(template.content) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Edit Message", fontWeight = FontWeight.Bold, fontSize = 20.sp)

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Spacer(Modifier.height(12.dp))

        Text("${content.length} characters", color = Color.Gray)

        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("{{Name}}", "{{Date}}", "{{Phone}}", "{{City}}").forEach {
                AssistChip(
                    onClick = { content += " $it " },
                    label = { Text(it.replace("{", "").replace("}", "")) }
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {

                // ✅ Save template
                vm.updateTemplate(template.copy(content = content))

                // ✅ CORRECT WhatsApp method (FIXED)
                val message = Uri.encode(content)

                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://wa.me/$phone?text=$message")
                )

                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Send via WhatsApp")
        }
    }
}