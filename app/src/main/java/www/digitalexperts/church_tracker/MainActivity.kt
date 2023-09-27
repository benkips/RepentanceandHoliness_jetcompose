package www.digitalexperts.church_tracker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import www.digitalexperts.church_tracker.Data.models.BottomNavigationItem
import www.digitalexperts.church_tracker.Navigation.SetupNavHost
import www.digitalexperts.church_tracker.ui.theme.RepentanceandHolinessTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import www.digitalexperts.church_tracker.Util.Constants


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            RepentanceandHolinessTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                var selectedItemIndex by rememberSaveable {
                    mutableStateOf(0)
                }
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val Scope = rememberCoroutineScope()
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

                ModalNavigationDrawer(
                    drawerContent = {
                        ModalDrawerSheet {
                            items.forEachIndexed { index, bottomNavigationItem ->
                                NavigationDrawerItem(
                                    label = { Text(text = bottomNavigationItem.title) },
                                    selected = index == selectedItemIndex,
                                    onClick = {
                                        selectedItemIndex = index
                                        Scope.launch {
                                            drawerState.close()
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (index == selectedItemIndex) {
                                                bottomNavigationItem.selectedIcon
                                            } else bottomNavigationItem.unselectedIcon,
                                            contentDescription = bottomNavigationItem.title
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
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                                    .clip(
                                        shape = RoundedCornerShape(12.dp),
                                    ),
                                backgroundColor = Color(0xFF0f82d2),
                                contentColor =Color(0xFFFFFFFF) ,
                                elevation = 8.dp,
                                title = { Text(text = "Repentance and Holliness App") },
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
                                            if(bottomNavigationItem.title.equals("Home")){
                                                navController.navigate(Constants.Screens.MAIN_SCREEN)
                                            }else if (bottomNavigationItem.title.equals("Teachings")){
                                                navController.navigate(Constants.Screens.TEACHINGS_SCREEN)
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
                        }
                    ) {
                        SetupNavHost(navController = navController)
                    }
                }


            }
        }
    }
}

