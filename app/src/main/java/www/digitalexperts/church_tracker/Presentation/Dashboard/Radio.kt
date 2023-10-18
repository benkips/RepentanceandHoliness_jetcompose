package www.digitalexperts.church_tracker.Presentation.Dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import www.digitalexperts.church_tracker.R
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun Radio() {
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
                PlayerButtons(modifier = Modifier.padding(vertical = 8.dp))
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
    playerButtonSize: Dp = 72.dp,
    sideButtonSize: Dp = 42.dp
) {
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
               /* if (audioFlag.value) {
                    audioFlag.value = false
                } else {
                    audioFlag.value = true
                }*/
            }

        Icon(
            painter = painterResource(R.drawable.ic_baseline_skip_next_24),
            contentDescription = "Skip Icon",
            modifier = buttonModifier,
            tint = Color.Black
        )


        Icon(
            painter =   painterResource(R.drawable.ic_baseline_play_arrow_24),
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
            onClick = { navController.navigate("webviews/$allotherlinks") },
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
            onClick = {  navController.navigate("webviews/$alltime")},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth(1f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
        ) {
            Text(
                text = "24/7 Endtime-Messages",
                color= Color.White,
                modifier = Modifier.padding(6.dp)

            )

        }
    }
}
