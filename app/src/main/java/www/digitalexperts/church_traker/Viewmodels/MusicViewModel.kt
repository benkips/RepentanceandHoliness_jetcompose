package www.digitalexperts.church_traker.Viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.media3.common.MediaItem
import dagger.hilt.android.lifecycle.HiltViewModel
import www.digitalexperts.church_traker.BackgroundServices.MusicServiceHandler
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class MusicViewModel  @Inject constructor(
    private val musicServiceHandler: MusicServiceHandler,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var isMusicPlaying by savedStateHandle.saveable { mutableStateOf(false) }
    fun setMusicItems() {
        val MEDIA_URL = "http://node-15.zeno.fm/gmdx1sb97f8uv?rj-ttl=5&rj-tok=AAABfccRdpIA8mopC5CghSrEoA"
        val mediaItem = MediaItem.fromUri(MEDIA_URL)
        musicServiceHandler.setMediaItem(mediaItem)
        musicServiceHandler.playPauseMusic()
        isMusicPlaying=true

    }
}