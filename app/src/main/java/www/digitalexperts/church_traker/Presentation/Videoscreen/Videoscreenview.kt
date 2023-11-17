package www.digitalexperts.church_traker.Presentation.Videoscreen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.media3.common.MimeTypes

const val MIME_TYPE_VIDEO_MP4 = MimeTypes.VIDEO_MP4
@Composable
fun Videoscreen(videoUrl : String){
    Log.d("videoUrl", "videoUrl: " + videoUrl)
    /*val video="https://eu2.contabostorage.com/77fef5a468e44fc8a9837b4ca1a047e4:videos/videos/"+videoUrl
    VideoPlayer(
        mediaItems = listOf(
            VideoPlayerMediaItem.NetworkMediaItem(
                url = "https://html5demos.com/assets/dizzy.mp4",
                mediaMetadata = MediaMetadata.Builder().setTitle("Clear MP4: Dizzy").build(),
                mimeType = MIME_TYPE_VIDEO_MP4,
            ),
        ),
        handleLifecycle = true,
        autoPlay = true,
        usePlayerController = true,
        enablePip = true,
        handleAudioFocus = true,
        controllerConfig = VideoPlayerControllerConfig.Default.copy(
            showSpeedAndPitchOverlay = false,
            showSubtitleButton = false,
            showCurrentTimeAndTotalTime = true,
            showBufferingProgress = false,
            showForwardIncrementButton = true,
            showBackwardIncrementButton = true,
            showBackTrackButton = true,
            showNextTrackButton = true,
            showRepeatModeButton = true,
            controllerShowTimeMilliSeconds = 5_000,
            controllerAutoShow = true,
        ),
        volume = 0.5f,  // volume 0.0f to 1.0f
        repeatMode = RepeatMode.NONE,       // or RepeatMode.ALL, RepeatMode.ONE
        onCurrentTimeChanged = { // long type, current player time (millisec)
            Log.e("CurrentTime", it.toString())
        },
        playerInstance = { // ExoPlayer instance (Experimental)
            addAnalyticsListener(
                object : AnalyticsListener {
                    // player logger
                }
            )
        },

    )*/
}