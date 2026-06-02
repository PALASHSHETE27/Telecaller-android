

package com.ele.telecallerapp.ui.screens.message

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ele.telecallerapp.viewmodel.MessageTemplateViewModel
import com.ele.telecallerapp.ui.components.MessageTemplateCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageTemplatesScreen(
    navController: NavController,
    phone: String,
    vm: MessageTemplateViewModel = viewModel()
) {
    val templates by vm.templates.collectAsState()
    var selectedId by remember { mutableStateOf<String?>(null) }

    val selectedTemplate = templates.find { it._id == selectedId }

    Scaffold(

        bottomBar = {

            Button(
                onClick = {
                    selectedTemplate?.let {
                        navController.navigate(
                            "edit_template/${it._id}/$phone"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = selectedId != null
            ) {
                Text("Select Template")
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier.padding(padding),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(templates) { template ->

                MessageTemplateCard(
                    template = template,
                    selected = selectedId == template._id,

                    onClick = {
                        selectedId = template._id
                    }
                )
            }
        }
    }
}