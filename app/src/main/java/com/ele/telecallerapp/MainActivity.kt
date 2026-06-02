

package com.ele.telecallerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.ele.telecallerapp.network.RetrofitClient
import com.ele.telecallerapp.ui.screens.*
import com.ele.telecallerapp.ui.screens.leads.*
import com.ele.telecallerapp.ui.screens.profile.EditProfileScreen
import com.ele.telecallerapp.ui.screens.campaign.CreateCampaignScreen
import com.ele.telecallerapp.ui.screens.message.*
import com.ele.telecallerapp.ui.screens.settings.SettingsScreen
import com.ele.telecallerapp.ui.theme.TelecallerAppTheme
import com.ele.telecallerapp.viewmodel.MessageTemplateViewModel
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.net.URLDecoder
import com.ele.telecallerapp.ui.screens.settings.NotificationSettingsScreen
import com.ele.telecallerapp.ui.screens.settings.LanguageRegionScreen


import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RetrofitClient.init(applicationContext)
        enableEdgeToEdge()
        setContent {
            TelecallerAppTheme {
                AppNav()
            }
        }
    }
}



@Composable
fun AppNav() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // LOGIN
        composable("login") {
            LoginScreen(
                onRegisterClick = { navController.navigate("register") },
                onForgotClick = { navController.navigate("forgot") },
                onLoginSuccess = {

                    navController.navigate("app") {
                        popUpTo("login") { inclusive = true } // 🔥 FIX
                        launchSingleTop = true
                    }
                }
            )
        }

        // REGISTER
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = { email ->
                    val encoded = URLEncoder.encode(email, "UTF-8")
                    navController.navigate("register_otp/$encoded")
                }
            )
        }

        // OTP
        composable("register_otp/{email}") { entry ->
            val email = URLDecoder.decode(entry.arguments?.getString("email")!!, "UTF-8")

            OtpVerifyScreen(
                email = email,
                onVerifySuccess = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }

        // FORGOT PASSWORD
        composable("forgot") {
            ForgotPasswordScreen(
                isChangePassword = false,
                onBackClick = { navController.popBackStack() },
                onSendClick = { email ->
                    val encoded = URLEncoder.encode(email, "UTF-8")
                    navController.navigate("forgot_otp/$encoded")
                }
            )
        }

        composable("forgot_otp/{email}") { entry ->
            val email = URLDecoder.decode(entry.arguments?.getString("email")!!, "UTF-8")

            ResetOtpScreen(
                email = email,
                onOtpVerified = { otp ->
                    val encodedOtp = URLEncoder.encode(otp, "UTF-8")
                    val encodedEmail = URLEncoder.encode(email, "UTF-8")

                    navController.navigate("new_password/$encodedEmail/$encodedOtp")
                }
            )
        }

        composable(
            "new_password/{email}/{otp}"
        ) { entry ->

            val email = URLDecoder.decode(entry.arguments?.getString("email")!!, "UTF-8")
            val otp = URLDecoder.decode(entry.arguments?.getString("otp")!!, "UTF-8")

            NewPasswordScreen(
                email = email,
                otp = otp,
                onPasswordReset = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // MAIN APP
        composable("app") {
            MainScaffold(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(rootNavController: NavHostController) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val appNavController = rememberNavController()

    val navBackStackEntry by
    appNavController.currentBackStackEntryAsState()

    val currentRoute =
        navBackStackEntry
            ?.destination
            ?.route
            ?.substringBefore("/")
            ?: "dashboard"

    // =========================================================
    // SCREEN TITLE
    // =========================================================

    val screenTitle = when {

        currentRoute.startsWith("message_templates") ->
            "Message Templates"

        currentRoute.startsWith("lead_details") ->
            "Lead Details"

        currentRoute.startsWith("edit_template") ->
            "Edit Template"

        currentRoute.startsWith("donor_details") ->
            "Donor Details"

        else -> when (currentRoute) {

            "dashboard" -> "Dashboard"

            "leads" -> "Leads"

            "campaigns" -> "Campaigns"

            "call_stats" -> "Call Stats"

            "edit_profile" -> "Profile"

            "settings" -> "Settings"

            "notifications" -> "Notifications"

            "call_settings" -> "Call Settings"

            "language_region" -> "Language & Region"

            "donation_form" -> "Donation Form"

            "prasadam_form" -> "Prasadam Form"

            "my_donors" -> "My Donors"

            else -> "Telecaller"
        }
    }

    // =========================================================
    // DRAWER
    // =========================================================

    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {

            SideDrawer(

                selectedItem = currentRoute,

                navController = appNavController,
                rootNavController = rootNavController,
                drawerScope = scope,

                drawerState = drawerState,

                onItemClick = { route ->

                    scope.launch {

                        drawerState.close()

                        appNavController.navigate(route) {

                            launchSingleTop = true

                            restoreState = true

                            popUpTo(
                                appNavController.graph.startDestinationId
                            ) {
                                saveState = true
                            }
                        }
                    }
                }
            )
        }
    ) {

        Scaffold(

            // =====================================================
            // MODERN TOP BAR
            // =====================================================

            topBar = {

                Surface(
                    tonalElevation = 6.dp,
                    shadowElevation = 8.dp,
                    color = Color(0xFF1976D2)
                ) {

                    CenterAlignedTopAppBar(

                        title = {

                            Column {

                                Text(
                                    text = screenTitle,

                                    style = MaterialTheme.typography.titleLarge,

                                    color = Color.White
                                )

                            }
                        },

                        navigationIcon = {

                            IconButton(

                                onClick = {

                                    scope.launch {
                                        drawerState.open()
                                    }
                                }

                            ) {

                                Surface(

                                    shape = MaterialTheme.shapes.medium,

                                    color = Color.White.copy(alpha = 0.15f)
                                ) {

                                    Icon(

                                        imageVector = Icons.Default.Menu,

                                        contentDescription = "Menu",

                                        tint = Color.White,

                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        },

                        actions = {

                            IconButton(
                                onClick = {
                                    appNavController.navigate("notifications")
                                }
                            ) {

                                Surface(

                                    shape = MaterialTheme.shapes.medium,

                                    color = Color.White.copy(alpha = 0.15f)
                                ) {

                                    Icon(

                                        imageVector = Icons.Default.Notifications,

                                        contentDescription = "Notifications",

                                        tint = Color.White,

                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(8.dp))
                        },

                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Transparent
                        )
                    )
                }
            },

            // =====================================================
            // BOTTOM BAR
            // =====================================================

            bottomBar = {

                BottomNavBar(
                    navController = appNavController
                )
            }

        ) { padding ->

            // =====================================================
            // NAV HOST
            // =====================================================

            NavHost(

                navController = appNavController,

                startDestination = "dashboard",

                modifier = Modifier.padding(padding)
            ) {

                // =====================================================
                // DASHBOARD
                // =====================================================

                composable("dashboard") {

                    DashboardScreen()
                }

                // =====================================================
                // LEADS
                // =====================================================

                composable("leads") {

                    LeadsScreen(

                        onOpen = { id ->

                            appNavController.navigate(
                                "lead_details/$id"
                            )
                        },

                        onAdd = {

                            appNavController.navigate(
                                "add_lead"
                            )
                        },

                        onMessageClick = { phone ->

                            appNavController.navigate(
                                "message_templates/$phone"
                            )
                        }
                    )
                }

                composable("add_lead") {

                    AddLeadScreen {

                        appNavController.popBackStack()
                    }
                }

                composable("lead_details/{id}") { backStackEntry ->

                    val id =
                        backStackEntry.arguments
                            ?.getString("id")!!

                    LeadDetailsScreen(
                        leadId = id
                    )
                }

                // =====================================================
                // MESSAGE TEMPLATES
                // =====================================================

                composable(

                    "message_templates/{phone}",

                    arguments = listOf(

                        navArgument("phone") {

                            type = NavType.StringType
                        }
                    )

                ) { backStackEntry ->

                    val phone =
                        backStackEntry.arguments
                            ?.getString("phone")!!

                    MessageTemplatesScreen(

                        navController = appNavController,

                        phone = phone
                    )
                }

                composable("message_templates") {

                    MessageTemplatesScreen(

                        navController = appNavController,

                        phone = ""
                    )
                }

                composable(
                    "edit_template/{id}/{phone}",
                    arguments = listOf(
                        navArgument("id") { type = NavType.StringType },
                        navArgument("phone") { type = NavType.StringType }
                    )
                ) { backStackEntry ->

                    val id = backStackEntry.arguments?.getString("id")!!
                    val phone = backStackEntry.arguments?.getString("phone")!!

                    val vm: MessageTemplateViewModel = viewModel()

                    val templates by vm.templates.collectAsState()

                    val template = templates.find { it._id == id }

                    if (template == null) {
                        Text("Template not found")
                        return@composable
                    }

                    EditMessageTemplateScreen(
                        template = template,
                        vm = vm,
                        phone = phone
                    )
                }

                // =====================================================
                // CAMPAIGNS
                // =====================================================

                composable("campaigns") {

                    CampaignsScreen(
                        navController = appNavController
                    )
                }

                composable("create_campaign") {

                    CreateCampaignScreen(
                        nav = appNavController
                    )
                }

                // =====================================================
                // CALL STATS
                // =====================================================

                composable("call_stats") {

                    CallStatsScreen()
                }

                // =====================================================
                // PROFILE
                // =====================================================

                composable("edit_profile") {

                    EditProfileScreen(
                        navController = appNavController
                    )
                }

                // =====================================================
                // SETTINGS
                // =====================================================

                composable("settings") {

                    SettingsScreen(
                        appNavController,
                        rootNavController
                    )
                }

                composable("notifications") {

                    NotificationSettingsScreen()
                }

                composable("language_region") {

                    LanguageRegionScreen()
                }

                // =====================================================
                // DONATIONS
                // =====================================================

                composable("prasadam_form") {

                    PrasadamFormScreen("Telecaller") {

                        appNavController.popBackStack()
                    }
                }

                composable("donation_form") {

                    RecordDonationScreen()
                }

                composable("my_donors") {

                    MyDonorsScreen(
                        navController = appNavController
                    )
                }

                composable(

                    "donor_details/{mobile}",

                    arguments = listOf(

                        navArgument("mobile") {

                            type = NavType.StringType
                        }
                    )

                ) { backStackEntry ->

                    val mobile =
                        backStackEntry.arguments
                            ?.getString("mobile")!!

                    DonorDetailsScreen(

                        mobile = mobile,

                        navController = appNavController
                    )
                }
            }
        }
    }
}
