package www.digitalexperts.church_traker.Presentation.Dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import www.digitalexperts.church_traker.Viewmodels.Healingviewmodel
import www.digitalexperts.church_traker.models.Healings

@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun Healingviews(navController: NavController, viewModel: Healingviewmodel) {
    val healingvideos = viewModel.Healed.collectAsLazyPagingItems()

    val listener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
        }

        override fun onEvents(
            player: Player,
            events: Player.Events
        ) {
            super.onEvents(player, events)
            // hide title only when player duration is at least 200ms
            if (player.currentPosition >= 200)
                viewModel.visibleTitle.value = false
        }

        override fun onPlaybackStateChanged(state: Int) {
            // Callback when the playback state changes
            when (state) {
                Player.STATE_IDLE, Player.STATE_BUFFERING -> {
                    // Handle buffering state
                    // Example: Media is buffering and not yet ready for playback
                    viewModel.isPlayerLoading.value = true
                }

                Player.STATE_READY, Player.STATE_ENDED -> {
                    // Handle ended state
                    // Example: Media playback has ended
                    viewModel.isPlayerLoading.value = false
                }
            }
        }


    }


    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            viewModel.player.removeListener(listener)
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
                .wrapContentSize()
        ) {
            AndroidView(
                factory = { context ->
                    PlayerView(context).also {
                        it.player = viewModel.player
                        viewModel.player.addListener(listener)
                    }
                },
                update = {
                    when (lifecycle) {
                        Lifecycle.Event.ON_PAUSE -> {
                            it.onPause()
                            it.player?.pause()
                        }

                        Lifecycle.Event.ON_RESUME -> {
                            it.onResume()
                        }

                        else -> Unit
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
            )

            if (viewModel.isPlayerLoading.value) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary, // Set the desired color for the ProgressBar
                    modifier = Modifier.align(Alignment.Center)
                )
            }



            this@Column.AnimatedVisibility(
                visible = viewModel.visibleTitle.value,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp),

                ) {
                viewModel.currentVideo.value?.video?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(16.dp)
                            .wrapContentHeight(),
                        color = Color.White,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Start
                    )
                }
            }


        }


        Spacer(modifier = Modifier.height(16.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            ElevatedButton(onClick = { viewModel.playVideo("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4".toUri()) }) {
                Text(text = "Play First Clip")
            }
            ElevatedButton(onClick = { viewModel.playVideo("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4".toUri()) }) {
                Text(text = "Play Second Clip")
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        // video playlist
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(videos) { item ->
                VideoCard(
                    isCurrentPlaying = viewModel.currentVideo.value == item,
                    videoItem = item,
                    onClick = { viewModel.playVideo(item.contentUri) },
                )
            }
        }

    }

}

@Composable
fun HealingScreen(thehealed: LazyPagingItems<Healings>, modifier: Modifier = Modifier) {
    when (thehealed.loadState.refresh) {
        LoadState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        is LoadState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Ooops something went wrong")
            }
        }
        else -> {
            LazyColumn(modifier = modifier) {
                itemsIndexed(thehealed) { index, item ->
                    item?.let {
                        BookItem(
                            book = item,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(getBackgroundForIndex(index))
                                .padding(vertical = 15.dp)
                        )
                    }
                }
            }
        }
    }
}