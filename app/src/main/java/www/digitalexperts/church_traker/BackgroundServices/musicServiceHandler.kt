package www.digitalexperts.church_traker.BackgroundServices

import android.app.Activity
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import javax.inject.Inject

class MusicServiceHandler @Inject constructor(
    private val exoPlayer: ExoPlayer
) : Player.Listener {
    val contexts = LocalContext
     init {
        exoPlayer.addListener(this)
    }

    fun setMediaItem(mediaItem: MediaItem) {
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
    }



    override fun onPlaybackStateChanged(playbackState: Int) {
        when (playbackState) {
            ExoPlayer.STATE_BUFFERING -> { }

            ExoPlayer.STATE_READY -> {
                exoPlayer.playWhenReady=true
            }

            Player.STATE_ENDED -> {
                // no-op
            }

            Player.STATE_IDLE -> {
                // no-op
            }
        }
    }


     fun playPauseMusic() {
            exoPlayer.play()
    }
    fun stopMusic() {
            exoPlayer.stop()

        }


    fun pauseMusic() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
        }

    }

    }


