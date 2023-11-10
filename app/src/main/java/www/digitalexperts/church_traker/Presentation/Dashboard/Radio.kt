package www.digitalexperts.church_traker.Presentation.Dashboard

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import www.digitalexperts.church_traker.R
import www.digitalexperts.church_traker.BackgroundServices.BackgroundPlayService
import www.digitalexperts.church_traker.BackgroundServices.MediaObject
import www.digitalexperts.church_traker.BackgroundServices.MusicServiceHandler
import www.digitalexperts.church_traker.BackgroundServices.PlayerService
import www.digitalexperts.church_traker.Viewmodels.Churchviewmodel
import www.digitalexperts.church_traker.Viewmodels.MusicViewModel

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

lateinit var backgroundPlayService: BackgroundPlayService
var player: Player? = null
lateinit var mediaObj: MediaObject
val TAG = "MainScreen"
private const val MEDIA_URL = "https://traffic.libsyn.com/secure/adbackstage/ADB162-1.5.mp3?dest-id=2710847"

 /*var connection = object : ServiceConnection {
    val contexts =  LocalContext

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.d(TAG, "onServiceConnected()")

        if (service is BackgroundPlayService.BackgroundServiceBinder){
             service.getService()
             service.getExoplayer()
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.d(TAG, "onServiceDisconnected()")
        Log.d(TAG, "unBindService()")
        backgroundPlayService.stopSelf()
        (contexts as Activity).applicationContext!!.unbindService(this)
    }


}*/


/*private val playerServiceConnection = object : ServiceConnection {
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as PlayerService.ServiceBinder
        player = binder.getPlayerService().player!!
        player?.prepare()
        player?.playWhenReady = false
        player?.seekTo(0, 0)
        val mediaItem = MediaItem.fromUri(MEDIA_URL)

        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.play()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        TODO("Not yet implemented")
    }
}*/



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
    val playerButtonSize: Dp = 72.dp
    val sideButtonSize: Dp = 42.dp

        Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val buttonModifier = modifier
            .size(sideButtonSize)
            .semantics { role = Role.Button }
        val midlebtnModifier = modifier
            .size(playerButtonSize)
            .semantics { role = Role.Button }
            .clickable {

                viewModel.setMusicItems()
                isMusicPlaying=viewModel.isMusicPlaying
                Toast
                    .makeText(context, isMusicPlaying.toString(), Toast.LENGTH_SHORT)
                    .show()
                isMusicPlaying=false
                /*initService(LocalContext.current)*/
                /* if (audioFlag.value) {
                    audioFlag.value = false
                } else {
                    audioFlag.value = true
                }*/
                /* Log.d(TAG, "initService()")
                val intent = Intent((context as Activity).applicationContext!!, PlayerService::class.java)
                context.applicationContext!!.bindService(intent, playerServiceConnection, Context.BIND_AUTO_CREATE)*/
            }

        Icon(
            painter = painterResource(R.drawable.ic_baseline_skip_next_24),
            contentDescription = "Skip Icon",
            modifier = buttonModifier,
            tint = Color.Black
        )


        Icon(
            painter =   painterResource( if (isMusicPlaying) R.drawable.ic_baseline_play_arrow_24
            else R.drawable.ic_baseline_pause_24),
            contentDescription = "Play / Pause Icon",
            tint = Color.Black,
            modifier = midlebtnModifier,
        )


        Icon(
            painter = painterResource(R.drawable.ic_baseline_skip_previous_24),
            contentDescription = "Replay 10 min",
            modifier = buttonModifier,
            tint = Color.Black
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
        ) {
            Text(
                text = "Other radio links",
                modifier = Modifier.padding(6.dp)
            )

        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { /* navController.navigate("webviews/$alltime")*/},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth(1f),
        ) {
            Text(
                text = "24/7 Endtime-Messages",
                color= Color.White,
                modifier = Modifier.padding(6.dp)

            )

        }
    }





}


