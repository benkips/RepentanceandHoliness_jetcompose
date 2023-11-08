package www.digitalexperts.church_traker.Viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.media3.common.MediaItem
import www.digitalexperts.church_traker.BackgroundServices.MusicServiceHandler
import javax.inject.Inject

class MusicViewModel  @Inject constructor(
    private val musicServiceHandler: MusicServiceHandler,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var isMusicPlaying by savedStateHandle.saveable { mutableStateOf(false) }
    fun setMusicItems() {
        val MEDIA_URL = "https://traffic.libsyn.com/secure/adbackstage/ADB162-1.5.mp3?dest-id=2710847"
        val mediaItem = MediaItem.fromUri(MEDIA_URL)
        musicServiceHandler.setMediaItem(mediaItem)
    }
}