package www.digitalexperts.church_traker.Presentation.Dashboard

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import www.digitalexperts.church_traker.R
import www.digitalexperts.church_traker.Viewmodels.MusicViewModel

import java.net.URLEncoder
import java.nio.charset.StandardCharsets


val TAG = "MainScreen"
private const val MEDIA_URL = "https://traffic.libsyn.com/secure/adbackstage/ADB162-1.5.mp3?dest-id=2710847"




@Composable
fun Radio(isMusicPlaying: Boolean,viewModel: MusicViewModel) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp, horizontal = 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(rememberScrollState())


        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Jsl Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .sizeIn(maxWidth = 100.dp, maxHeight = 100.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            SongDescription("JESUS IS LORD RADIO", "streaming live")
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(10f)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                PlayerButtons(modifier = Modifier.padding(vertical = 8.dp),viewModel)
                Otherbtns()
            }


        }
    }

}
@Composable
fun SongDescription(
    title: String,
    name: String
) {
    Text(
        text = title,
        style = MaterialTheme.typography.h5,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = Color.Gray,
        fontWeight = FontWeight.Bold
    )

    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = name,
            style = MaterialTheme.typography.body2,
            maxLines = 1,
            color = Color.Gray
        )
    }
}
@Composable
fun PlayerButtons(
    modifier: Modifier = Modifier,
    viewModel: MusicViewModel
) {
    val context=LocalContext.current
    var isMusicPlaying= viewModel.isMusicPlaying
    val audioFlag = remember { mutableStateOf(true) }
    val playerButtonSize: Dp = 72.dp
    val sideButtonSize: Dp = 42.dp

        Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val refreshModifier = modifier
            .size(sideButtonSize)
            .semantics { role = Role.Button }
            .clickable {
                val url = "https://s3.radio.co/s97f38db97/listen"
                viewModel.setMusicItems(url)
            }

       val stopbtnModifier = modifier
                .size(sideButtonSize)
                .semantics { role = Role.Button }
                .clickable {
                    viewModel.stopMusic()
                }

        val midlebtnModifier = modifier
                .size(playerButtonSize)
                .semantics { role = Role.Button }
                .clickable {
                    if (audioFlag.value) {
                        val url = "https://s3.radio.co/s97f38db97/listen"
                        viewModel.setMusicItems(MEDIA_URL)
                        audioFlag.value = false

                    } else {
                        viewModel.pauseMusic()
                        audioFlag.value = true
                    }
                    Toast
                        .makeText(context,  audioFlag.value.toString() , Toast.LENGTH_SHORT)
                        .show()
                }

        Icon(
            painter = painterResource(R.drawable.baseline_refresh_24),
            contentDescription = "Skip Icon",
            modifier = refreshModifier,
            tint = Color.Black
        )


        Icon(
            painter =   painterResource( if (audioFlag.value) R.drawable.ic_baseline_play_arrow_24
            else R.drawable.ic_baseline_pause_24 ),
            contentDescription = "Play / Pause Icon",
            tint = Color.Black,
            modifier = midlebtnModifier,
        )


        Icon(
            painter = painterResource(R.drawable.baseline_stop_24),
            contentDescription = "stopButton",
            tint = Color.Black,
            modifier = stopbtnModifier
        )


    }
}

@Composable
fun Otherbtns() {
    val allotherlinks=
        URLEncoder.encode("https://repentanceandholinessinfo.com/playradio.php", StandardCharsets.UTF_8.toString())
    val alltime= URLEncoder.encode("http://node-15.zeno.fm/gmdx1sb97f8uv?rj-ttl=5&rj-tok=AAABfccRdpIA8mopC5CghSrEoA", StandardCharsets.UTF_8.toString())
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Button(
            onClick = { /*navController.navigate("webviews/$allotherlinks")*/ },
            shape = RoundedCornerShape(10.dp),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 6.dp,
                pressedElevation = 8.dp,
                disabledElevation = 0.dp
            ),
            modifier = Modifier.fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0f82d2))
        ) {
            Text(
                text = "Other radio links",
                modifier = Modifier.padding(6.dp),
                color= Color.White,
            )

        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { /* navController.navigate("webviews/$alltime")*/},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth(1f),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0f82d2))
        ) {
            Text(
                text = "24/7 Endtime-Messages",
                color= Color.White,
                modifier = Modifier.padding(6.dp)

            )

        }
    }





}


