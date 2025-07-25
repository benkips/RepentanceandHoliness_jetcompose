package www.digitalexperts.church_traker

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.MobileAds
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import www.digitalexperts.church_traker.BackgroundServices.MediaService
import www.digitalexperts.church_traker.Data.models.BottomNavigationItem
import www.digitalexperts.church_traker.Data.models.SideNavigationItem
import www.digitalexperts.church_traker.Navigation.SetupNavHost
import www.digitalexperts.church_traker.Util.Constants
import www.digitalexperts.church_traker.Util.PermissionManager
import www.digitalexperts.church_traker.ui.theme.RepentanceandHolinessTheme
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    val TOPIC = "Alertstwo"
    private lateinit var permissionManager: PermissionManager

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        MobileAds.initialize(
            this
        ) { }
        permissionManager = PermissionManager(this)

        var android13perm = ""

        setContent {
            RepentanceandHolinessTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                var selectedItemIndex by rememberSaveable {
                    mutableStateOf(0)
                }
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val Scope = rememberCoroutineScope()
                var weblink = ""

                FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

                //creating list
                var android13perm = ""
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    android13perm = android.Manifest.permission.POST_NOTIFICATIONS
                } else {
                    android13perm = android.Manifest.permission.READ_EXTERNAL_STORAGE
                }

                val permissions = arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android13perm
                )

                permissionManager.requestPermissions(permissions)


                val items = listOf(
                    BottomNavigationItem(
                        title = "Home",
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                        hasNews = false,
                    ),
                    BottomNavigationItem(
                        title = "Teachings",
                        selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_baseline_local_movies_24),
                        unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_baseline_local_movies_24),
                        hasNews = false,
                    ),
                    BottomNavigationItem(
                        title = "Pdf",
                        selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_baseline_local_library_24),
                        unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_baseline_local_library_24),
                        hasNews = false,
                    ),
                    BottomNavigationItem(
                        title = "Live Radio",
                        selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_jslrd),
                        unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_jslrd),
                        hasNews = false,
                    )

                )

                val sidenavitems = listOf(
                    SideNavigationItem(
                        title = "Home",
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                        hasNews = false,
                    ),
                    SideNavigationItem(
                        title = "JESUS IS LORD  Radio",
                        selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_jslrd),
                        unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_jslrd),
                        hasNews = false,
                    ),
                    SideNavigationItem(
                        title = "Visitations of the LORD",
                        selectedIcon = ImageVector.vectorResource(id = R.drawable.baseline_bubble_chart_24),
                        unselectedIcon = ImageVector.vectorResource(id = R.drawable.baseline_bubble_chart_24),
                        hasNews = false,
                    ),
                    SideNavigationItem(
                        title = "Prophecies and Fulfilments",
                        selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_baseline_offline_bolt_24),
                        unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_baseline_offline_bolt_24),
                        hasNews = false,
                    ),
                    SideNavigationItem(
                        title = "Healings of the LORD",
                        selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_baseline_whatshot_24),
                        unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_baseline_whatshot_24),
                        hasNews = false,
                    ),
                    SideNavigationItem(
                        title = "Twitter Conversations",
                        selectedIcon = ImageVector.vectorResource(id = R.drawable.twitter),
                        unselectedIcon = ImageVector.vectorResource(id = R.drawable.twitter),
                        hasNews = false,
                    ),
                    SideNavigationItem(
                        title = "Facebook",
                        selectedIcon = ImageVector.vectorResource(id = R.drawable.baseline_add_to_home_screen_24),
                        unselectedIcon = ImageVector.vectorResource(id = R.drawable.baseline_add_to_home_screen_24),
                        hasNews = false,
                    ),
                    SideNavigationItem(
                        title = "Instagram",
                        selectedIcon = ImageVector.vectorResource(id = R.drawable.instagram),
                        unselectedIcon = ImageVector.vectorResource(id = R.drawable.instagram),
                        hasNews = false,
                    ),
                    SideNavigationItem(
                        title = "Contacts",
                        selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_baseline_contact_support_24),
                        unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_baseline_contact_support_24),
                        hasNews = false,
                    ),
                    SideNavigationItem(
                        title = "Share",
                        selectedIcon = ImageVector.vectorResource(id = R.drawable.baseline_share_24),
                        unselectedIcon = ImageVector.vectorResource(id = R.drawable.baseline_share_24),
                        hasNews = false,
                    )

                )
                ModalNavigationDrawer(
                    drawerContent = {
                        ModalDrawerSheet {
                            sidenavitems.forEachIndexed { index, sideNavigationItem ->
                                NavigationDrawerItem(
                                    label = { Text(text = sideNavigationItem.title) },
                                    selected = index == selectedItemIndex,
                                    onClick = {
                                        selectedItemIndex = index
                                        Scope.launch {
                                            drawerState.close()
                                        }

                                        if (sideNavigationItem.title.equals("Home")) {
                                            navController.navigate(Constants.Screens.MAIN_SCREEN)
                                        } else if (sideNavigationItem.title.equals("Healings of the LORD")) {
                                            navController.navigate(Constants.Screens.HEALING_SCREEN)
                                        } else if (sideNavigationItem.title.equals("JESUS IS LORD  Radio")) {
                                            navController.navigate(Constants.Screens.RADIO_SCREEN)
                                        } else if (sideNavigationItem.title.equals("Visitations of the LORD")) {
                                            weblink =
                                                "https://repentanceandholinessinfo.com/visitations.php"
                                            weblink = URLEncoder.encode(
                                                weblink,
                                                StandardCharsets.UTF_8.toString()
                                            )
                                            navController.navigate("webviews/$weblink")
                                        } else if (sideNavigationItem.title.equals("Prophecies and Fulfilments")) {
                                            weblink =
                                                "https://repentanceandholinessinfo.com/prophecies.php"
                                            weblink = URLEncoder.encode(
                                                weblink,
                                                StandardCharsets.UTF_8.toString()
                                            )
                                            navController.navigate("webviews/$weblink")
                                        } else if (sideNavigationItem.title.equals("Twitter Conversations")) {
                                            weblink =
                                                "https://repentanceandholinessinfo.com/twitter.php"
                                            weblink = URLEncoder.encode(
                                                weblink,
                                                StandardCharsets.UTF_8.toString()
                                            )
                                            navController.navigate("webviews/$weblink")
                                        } else if (sideNavigationItem.title.equals("Facebook")) {
                                            weblink =
                                                "https://web.facebook.com/jesusiscomingofficial/?_rdc=1&_rdr"
                                            weblink = URLEncoder.encode(
                                                weblink,
                                                StandardCharsets.UTF_8.toString()
                                            )
                                            navController.navigate("webviews/$weblink")
                                        } else if (sideNavigationItem.title.equals("Instagram")) {
                                            weblink = "https://www.instagram.com/jesusiscoming_2/"
                                            weblink = URLEncoder.encode(
                                                weblink,
                                                StandardCharsets.UTF_8.toString()
                                            )
                                            navController.navigate("webviews/$weblink")
                                        } else if (sideNavigationItem.title.equals("Contacts")) {
                                            navController.navigate(Constants.Screens.CALL_SCREEN)
                                        } else if (sideNavigationItem.title.equals("Share")) {
                                            val sendIntent = Intent()
                                            sendIntent.action = Intent.ACTION_SEND
                                            sendIntent.putExtra(
                                                Intent.EXTRA_TEXT,
                                                "Repentance and Holiness android app on playstore\n https://play.google.com/store/apps/details?id=www.digitalexperts.church_traker&hl=en"
                                            )
                                            sendIntent.type = "text/plain"
                                            startActivity(sendIntent)
                                        }


                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (index == selectedItemIndex) {
                                                sideNavigationItem.selectedIcon
                                            } else sideNavigationItem.unselectedIcon,
                                            contentDescription = sideNavigationItem.title
                                        )
                                    })
                            }
                        }
                    },
                    drawerState = drawerState
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                modifier = Modifier
                                    .padding(
                                        WindowInsets.safeDrawing.asPaddingValues()
                                    ).padding(horizontal = 12.dp, vertical = 6.dp)
                                    .clip(
                                        shape = RoundedCornerShape(8.dp),
                                    ),
                                backgroundColor = Color(0xFF0f82d2),
                                contentColor = Color(0xFFFFFFFF),
                                elevation = 8.dp,
                                title = { Text(text = "Repentance and Holiness") },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        Scope.launch {
                                            drawerState.open()
                                        }
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Menu,
                                            contentDescription = "menu icon",
                                            tint = Color(0xFFFFFFFF)
                                        )
                                    }
                                }
                            )
                        },
                        bottomBar = {
                            NavigationBar(
                                modifier = Modifier
                                    .clip(
                                        shape = RoundedCornerShape(
                                            topStart = 30.dp,
                                            topEnd = 30.dp
                                        )
                                    ),
                                // containerColor = Color(0xFFFFFFFF),
                                contentColor = Color(0xFF888888)
                            ) {
                                items.forEachIndexed { index, bottomNavigationItem ->

                                    NavigationBarItem(
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            selectedItemIndex = index
                                            if (bottomNavigationItem.title.equals("Home")) {
                                                navController.navigate(Constants.Screens.MAIN_SCREEN)
                                            } else if (bottomNavigationItem.title.equals("Teachings")) {
                                                navController.navigate(Constants.Screens.TEACHINGS_SCREEN)
                                            } else if (bottomNavigationItem.title.equals("Pdf")) {
                                                navController.navigate(Constants.Screens.MAGAZINE_SCREEN)
                                            } else if (bottomNavigationItem.title.equals("Live Radio")) {
                                                navController.navigate(Constants.Screens.RADIO_SCREEN)
                                                startMusicService()
                                            }
                                            //navController.navigate(Constants.Screens.CHURCH_DETAIL_SCREEN)
                                        },
                                        icon = {
                                            BadgedBox(
                                                badge = {
                                                    if (bottomNavigationItem.badgeCount != null) {
                                                        Badge {
                                                            Text(text = bottomNavigationItem.badgeCount.toString())
                                                        }
                                                    } else if (bottomNavigationItem.hasNews) {
                                                        Badge()
                                                    }
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = if (index == selectedItemIndex) {
                                                        bottomNavigationItem.selectedIcon
                                                    } else bottomNavigationItem.unselectedIcon,
                                                    contentDescription = bottomNavigationItem.title
                                                )
                                            }
                                        },
                                        label = { Text(text = bottomNavigationItem.title) })

                                }
                            }
                        },
                         contentWindowInsets = WindowInsets.safeDrawing,
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier.padding(
                                PaddingValues(
                                    0.dp,
                                    0.dp,
                                    0.dp,
                                    innerPadding.calculateBottomPadding()
                                )
                            )
                        ) {
                            SetupNavHost(navController = navController)
                        }
                    }
                }


            }

        }


    }


    private fun startMusicService() {
        val intent = Intent(this, MediaService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}

