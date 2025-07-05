package www.digitalexperts.church_traker.Presentation.Videoscreen

import android.app.Activity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.paging.compose.LazyPagingItems
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSourceFactory
import androidx.media3.common.util.Util
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import www.digitalexperts.church_traker.R
import www.digitalexperts.church_traker.Util.DownloadFile
import www.digitalexperts.church_traker.models.Healings


@Composable
fun ShortViewCompose(
    activity: Activity,
    videoItemsUrl:LazyPagingItems<Healings>,
    clickItemPosition:Int = 0,
    videoHeader:@Composable () -> Unit = {},
    videoBottom:@Composable () -> Unit = {}
) {
    /*activity.immersive(darkMode = true)*/
    val pagerState: PagerState = run {
        remember {
            PagerState(clickItemPosition, 0, videoItemsUrl.itemCount - 1)
        }
    }
    val initialLayout= remember {
        mutableStateOf(true)
    }
    val pauseIconVisibleState = remember {
        mutableStateOf(false)
    }
    Pager(
        state = pagerState,
        orientation = Orientation.Vertical,
        offscreenLimit = 1
    ) {
        pauseIconVisibleState.value=false
        SingleVideoItemContent(videoItemsUrl[page],
            pagerState,
            page,
            initialLayout,
            pauseIconVisibleState,
            videoHeader,
            videoBottom)
    }

    LaunchedEffect(clickItemPosition){
        delay(300)
        initialLayout.value=false
    }

}

@Composable
private fun SingleVideoItemContent(
    videoUrl: Healings?,
    pagerState: PagerState,
    pager: Int,
    initialLayout: MutableState<Boolean>,
    pauseIconVisibleState: MutableState<Boolean>,
    VideoHeader: @Composable() () -> Unit,
    VideoBottom: @Composable() () -> Unit,
) {
    val contextForToast = LocalContext.current.applicationContext
    Box(modifier = Modifier.fillMaxSize()){
        VideoPlayer(videoUrl,pagerState,pager,pauseIconVisibleState)
        VideoHeader.invoke()
        Box(modifier = Modifier.align(Alignment.BottomStart)){
            VideoBottom.invoke()
        }
        if (initialLayout.value) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black))
        }



        FloatingActionButton(
            modifier = Modifier
                .padding(bottom = 50.dp, end = 20.dp)
                .align(alignment = Alignment.BottomEnd),
            backgroundColor =  Color(0xFFD81B60),
            contentColor = Color.White,
            onClick = {

                Toast.makeText(contextForToast, "Scroll up to see more videos", Toast.LENGTH_SHORT).show()

            }
        ) {
            Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = "UP")
        }

        FloatingActionButton(
            modifier = Modifier
                .padding(bottom = 50.dp, start = 20.dp)
                .align(alignment = Alignment.BottomStart),
            backgroundColor =  Color(0xFFD81B60),
            contentColor = Color.White,
            onClick = {
                var downloadFile=DownloadFile(contextForToast)
                downloadFile.downloadFile(videoUrl!!.vidlink)
                Toast.makeText(contextForToast, "Downloading ...check your downloads", Toast.LENGTH_SHORT).show()

            }
        ) {
            Icon( painter = painterResource(R.drawable.baseline_file_download_24), contentDescription = "UP")
        }
    }
}

@Composable
fun VideoPlayer(
    videoUrl: Healings?,
    pagerState: PagerState,
    pager: Int,
    pauseIconVisibleState: MutableState<Boolean>,
) {
    val context = LocalContext.current
    val scope= rememberCoroutineScope()
    val  mediaItem= MediaItem.fromUri(videoUrl!!.vidlink)
    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                    context,
                    Util.getUserAgent(context, context.packageName)
                )
                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaItem)

                this.prepare(source)
            }
    }
    if (pager == pagerState.currentPage) {
        exoPlayer.playWhenReady = true
        exoPlayer.play()
    } else {
        exoPlayer.pause()
    }
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    exoPlayer.repeatMode = Player.REPEAT_MODE_ONE

    DisposableEffect(
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)

        ){
            AndroidView(factory = {
                PlayerView(context).apply {
                    hideController()
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT

                    player = exoPlayer
                    layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                }
            },modifier = Modifier.noRippleClickable {
                pauseIconVisibleState.value=true
                exoPlayer.pause()
                scope.launch {
                    delay(500)
                    if (exoPlayer.isPlaying) {
                        exoPlayer.pause()
                    } else {
                        pauseIconVisibleState.value=false
                        exoPlayer.play()
                    }
                }
            })
            if (pauseIconVisibleState.value)
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(80.dp))
            Text(
                text = videoUrl!!.message,
                color= Color.White,
                modifier = Modifier
                    .padding(top = 100.dp)
                    .align(Alignment.TopCenter)


            )
        }
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
}
@Composable
private fun VideoActions(
    modifier: Modifier = Modifier,
    onDownloadClick: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        onDownloadClick?.let {
            IconButton(onClick = it) {
                Icon(
                    painter = painterResource(R.drawable.baseline_file_download_24),
                    contentDescription = "download",
                )
            }
        }
    }
}
