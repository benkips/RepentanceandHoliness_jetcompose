package www.digitalexperts.church_traker.Presentation.Tablayout

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import www.digitalexperts.church_traker.Presentation.Dashboard.CommonWebinfo
import www.digitalexperts.church_traker.Presentation.Dashboard.Radio
import www.digitalexperts.church_traker.Viewmodels.MusicViewModel

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun TabScreen(viewModelForMusic: MusicViewModel, navController: NavController) {

    val act = LocalContext.current
    var selectedTab by remember {
        mutableStateOf(0)
    }

    val titleAndIcons = listOf(
        "LISTEN" to ImageVector.vectorResource(id = www.digitalexperts.church_traker.R.drawable.ic_baseline_local_library_24),
        "LIVE" to ImageVector.vectorResource(id = www.digitalexperts.church_traker.R.drawable.ic_baseline_slow_motion_video_24),
        /*"Profile" to ImageVector.vectorResource(id = www.digitalexperts.church_traker.R.drawable.ic_baseline_local_library_24)*/

    )
    Scaffold(topBar = {
        Column {

            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),

            ) {
                titleAndIcons.forEachIndexed { index, (title, icon) ->
                    Tab(selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(text = title, maxLines = 2) },
                        icon = { Icon(icon, contentDescription = null) })

                }

            }


        }
    }, content = {innerPadding ->

        when(selectedTab){
            0 -> Radio(innerPadding,isMusicPlaying = viewModelForMusic.isMusicPlaying, viewModelForMusic, navController)
            1 -> CommonWebinfo("https://repentanceandholinessinfo.com/livevent.php")
           /* 2-> CallScreen()*/
        }

    })
}