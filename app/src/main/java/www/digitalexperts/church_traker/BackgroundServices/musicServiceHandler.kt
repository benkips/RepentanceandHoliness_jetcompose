package www.digitalexperts.church_traker.BackgroundServices

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import javax.inject.Inject

class MusicServiceHandler @Inject constructor(
    private val exoPlayer: ExoPlayer,
) : Player.Listener {
     init {
        exoPlayer.addListener(this)
    }

    fun setMediaItem(mediaItem: MediaItem) {
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
    }



    override fun onPlaybackStateChanged(playbackState: Int) {
        when (playbackState) {
            ExoPlayer.STATE_BUFFERING -> {}

            ExoPlayer.STATE_READY -> {}

            Player.STATE_ENDED -> {
                // no-op
            }

            Player.STATE_IDLE -> {
                // no-op
            }
        }
    }


    private suspend fun playPauseMusic() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()

        } else {
            exoPlayer.play()
            /*_musicStates.value = MusicStates.MediaPlaying(
                isPlaying = true
            )*/

        }
    }


}