package www.digitalexperts.church_traker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import www.digitalexperts.church_traker.Viewmodels.Healingviewmodel
import www.digitalexperts.church_traker.ui.theme.RepentanceandHolinessTheme
@AndroidEntryPoint
class VideoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RepentanceandHolinessTheme {
                val healingviewmodel= hiltViewModel<Healingviewmodel>()
                Surface(color = MaterialTheme.colorScheme.background) {
                  /*  ShortViewCompose(
                        activity = this,
                        videoItemsUrl = videoUrls,
                        clickItemPosition = 0
                    )*/
                }
            }
        }


    }
}


val videoUrls = listOf(
    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4"
)