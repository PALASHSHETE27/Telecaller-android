
package com.ele.telecallerapp.ui.screens

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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ele.telecallerapp.viewmodel.DonationViewModel
import com.ele.telecallerapp.viewmodel.DonorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonorDetailsScreen(
    mobile: String,
    navController: NavController,
    vm: DonorViewModel = viewModel(),
    donationVm: DonationViewModel = viewModel()
) {

    val donors by vm.donors.collectAsState()
    val donations by donationVm.donations.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        vm.loadDonors()
        donationVm.loadDonations(mobile)
    }

    val donor = donors.find { it.mobile == mobile }

    if (donor == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color(0xFF2F80ED)
            )
        }
        return
    }

    var editMode by remember { mutableStateOf(false) }

    var name by remember {
        mutableStateOf(TextFieldValue(donor.donorName))
    }

    var location by remember {
        mutableStateOf(donor.location)
    }

    var type by remember {
        mutableStateOf(donor.donorType)
    }

    Scaffold(
        containerColor = Color(0xFFF5F8FF),

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "Donor Details",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),

                actions = {

                    IconButton(
                        onClick = {
                            editMode = !editMode
                        }
                    ) {

                        Icon(
                            Icons.Default.Edit,
                            contentDescription = null,
                            tint = Color(0xFF2F80ED)
                        )
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp)

        ) {

            item {
                Spacer(modifier = Modifier.height(6.dp))
            }

            // ---------------- TOP PROFILE CARD ----------------

            item {

                Card(

                    modifier = Modifier.fillMaxWidth(0.92f),

                    shape = RoundedCornerShape(32.dp),

                    elevation = CardDefaults.cardElevation(8.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )

                ) {

                    Box(

                        modifier = Modifier
                            .fillMaxWidth()

                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color(0xFF2F80ED),
                                        Color(0xFF56CCF2)
                                    )
                                )
                            )
                    ) {

                        Column(

                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),

                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Box(

                                modifier = Modifier
                                    .size(100.dp)
                                    .background(
                                        Color.White,
                                        CircleShape
                                    ),

                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    text = name.text.first().toString(),
                                    fontSize = 34.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF2F80ED)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            if (editMode) {

                                OutlinedTextField(

                                    value = name,

                                    onValueChange = {
                                        name = it
                                    },

                                    modifier = Modifier.fillMaxWidth(),

                                    shape = RoundedCornerShape(18.dp),

                                    textStyle = TextStyle(
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    ),

                                    label = {

                                        Text(
                                            "Donor Name",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    },

                                    colors = OutlinedTextFieldDefaults.colors(

                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,

                                        focusedBorderColor = Color.Transparent,
                                        unfocusedBorderColor = Color.Transparent,

                                        focusedLabelColor = Color.Black,
                                        unfocusedLabelColor = Color.Black,

                                        cursorColor = Color.Black
                                    )
                                )

                            } else {

                                Text(
                                    text = name.text,
                                    color = Color.White,
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = type.ifBlank { "Active Donor" },
                                color = Color.White.copy(alpha = 0.92f),
                                fontSize = 14.sp
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Card(

                                shape = RoundedCornerShape(22.dp),

                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                ),

                                elevation = CardDefaults.cardElevation(4.dp)

                            ) {

                                Column(

                                    modifier = Modifier.padding(
                                        horizontal = 34.dp,
                                        vertical = 16.dp
                                    ),

                                    horizontalAlignment = Alignment.CenterHorizontally

                                ) {

                                    Text(
                                        text = "Total Lifetime Giving",
                                        color = Color.Gray,
                                        fontSize = 13.sp
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = "₹${donor.totalGiven}",
                                        color = Color(0xFF5B2EFF),
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 30.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // ---------------- ACTION BUTTONS ----------------

            item {

                Row(

                    modifier = Modifier.fillMaxWidth(0.92f),

                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    ActionCircleButton(
                        text = "Call",
                        icon = Icons.Default.Call,
                        background = Color(0xFFE9F7EF),
                        iconColor = Color(0xFF27AE60)
                    ) {

                        context.startActivity(
                            Intent(
                                Intent.ACTION_DIAL,
                                Uri.parse("tel:${donor.mobile}")
                            )
                        )
                    }

                    ActionCircleButton(
                        text = "Message",
                        icon = Icons.Default.Message,
                        background = Color(0xFFFFF3E6),
                        iconColor = Color(0xFFFF9800)
                    ) {

                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("sms:${donor.mobile}")
                            )
                        )
                    }

                    ActionCircleButton(
                        text = "Maps",
                        icon = Icons.Default.Map,
                        background = Color(0xFFEAF2FF),
                        iconColor = Color(0xFF2F80ED)
                    ) {

                        val mapUri =
                            Uri.parse("geo:0,0?q=${Uri.encode(donor.location)}")

                        context.startActivity(
                            Intent(Intent.ACTION_VIEW, mapUri)
                        )
                    }

                    ActionCircleButton(
                        text = "Edit",
                        icon = Icons.Default.Edit,
                        background = Color(0xFFF3EAFF),
                        iconColor = Color(0xFF8E44AD)
                    ) {
                        editMode = true
                    }
                }
            }

            // ---------------- GENERAL DETAILS ----------------

            item {

                Card(

                    modifier = Modifier.fillMaxWidth(0.92f),

                    shape = RoundedCornerShape(24.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),

                    elevation = CardDefaults.cardElevation(5.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {

                        Text(
                            text = donor.donorType.ifBlank {
                                "DONOR DETAILS"
                            },

                            color = Color(0xFF2F80ED),

                            fontWeight = FontWeight.Bold,

                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        ContactRow(
                            icon = Icons.Default.Phone,
                            label = "Mobile",
                            value = donor.mobile
                        ) {

                            context.startActivity(
                                Intent(
                                    Intent.ACTION_DIAL,
                                    Uri.parse("tel:${donor.mobile}")
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        ContactRow(
                            icon = Icons.Default.LocationOn,
                            label = "Location",
                            value = donor.location.ifBlank {
                                "Location not available"
                            }
                        ) {}

                        Spacer(modifier = Modifier.height(18.dp))

                        Box(

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .background(Color(0xFFEAF3FF))
                                .clickable {

                                    val mapUri =
                                        Uri.parse("geo:0,0?q=${Uri.encode(donor.location)}")

                                    context.startActivity(
                                        Intent(Intent.ACTION_VIEW, mapUri)
                                    )
                                },

                            contentAlignment = Alignment.Center
                        ) {

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Icon(
                                    Icons.Default.Map,
                                    contentDescription = null,
                                    tint = Color(0xFF2F80ED),
                                    modifier = Modifier.size(34.dp)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    "Open in Maps",
                                    color = Color(0xFF2F80ED),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            // ---------------- DONATION HISTORY TITLE ----------------

            item {

                Row(

                    modifier = Modifier
                        .fillMaxWidth(0.92f),

                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        Icons.Default.History,
                        contentDescription = null,
                        tint = Color(0xFF2F80ED)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Donation History",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
            }

            // ---------------- DONATION LIST ----------------

            if (donations.isEmpty()) {

                item {

                    Text(
                        "No donations found",
                        color = Color.Gray,
                        modifier = Modifier.padding(20.dp)
                    )
                }

            } else {

                items(donations) { donation ->

                    DonationHistoryCard(
                        title = donation.paymentType,
                        amount = "₹${donation.amount}",
                        date = donation.date,
                        status = "Completed"
                    )
                }
            }

            // ---------------- SAVE BUTTON ----------------

            if (editMode) {

                item {

                    Button(

                        onClick = {

                            vm.updateDonor(

                                donor.copy(
                                    donorName = name.text,
                                    location = location,
                                    donorType = type
                                )

                            ) {

                                editMode = false
                            }
                        },

                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .height(58.dp),

                        shape = RoundedCornerShape(18.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2F80ED)
                        )
                    ) {

                        Icon(
                            Icons.Default.Save,
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            "Save Changes",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

// ---------------- ACTION BUTTON ----------------

@Composable
fun ActionCircleButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    background: Color,
    iconColor: Color,
    onClick: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(

            modifier = Modifier
                .size(66.dp)
                .clickable { onClick() },

            shape = CircleShape,

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),

            elevation = CardDefaults.cardElevation(5.dp)
        ) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Box(

                    modifier = Modifier
                        .size(46.dp)
                        .background(
                            background,
                            CircleShape
                        ),

                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = text,
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}

// ---------------- CONTACT ROW ----------------

@Composable
fun ContactRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    onClick: () -> Unit = {}
) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },

        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(2.dp)
    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(

                modifier = Modifier
                    .size(42.dp)
                    .background(
                        Color(0xFFEAF2FF),
                        CircleShape
                    ),

                contentAlignment = Alignment.Center
            ) {

                Icon(
                    icon,
                    contentDescription = null,
                    tint = Color(0xFF2F80ED)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    label,
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    value,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
        }
    }
}

// ---------------- DONATION CARD ----------------

@Composable
fun DonationHistoryCard(
    title: String,
    amount: String,
    date: String,
    status: String
) {

    Card(

        modifier = Modifier.fillMaxWidth(0.92f),

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(5.dp)
    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(

                modifier = Modifier
                    .size(50.dp)
                    .background(
                        Color(0xFFEAF2FF),
                        CircleShape
                    ),

                contentAlignment = Alignment.Center
            ) {

                Icon(
                    Icons.Default.CurrencyRupee,
                    contentDescription = null,
                    tint = Color(0xFF2F80ED)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    date,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {

                Text(
                    amount,
                    color = Color(0xFF1B5E20),
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Box(

                    modifier = Modifier
                        .background(
                            Color(0xFFE8F5E9),
                            RoundedCornerShape(10.dp)
                        )
                        .padding(
                            horizontal = 10.dp,
                            vertical = 4.dp
                        )
                ) {

                    Text(
                        status,
                        color = Color(0xFF1B5E20),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}