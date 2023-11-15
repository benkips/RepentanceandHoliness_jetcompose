package www.digitalexperts.church_traker.Viewmodels

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import www.digitalexperts.church_traker.Repo.Repostuff
import www.digitalexperts.church_traker.models.Healings
import javax.inject.Inject

@HiltViewModel
class Healingviewmodel @Inject constructor(
    private  val repostuff: Repostuff,
    private val savedStateHandle: SavedStateHandle,
    val player: Player
    ) : ViewModel() {
    val Healed = repostuff.gethealingz()

    private val _videos = savedStateHandle.getStateFlow("videoUris", mutableListOf<Healings>())
    val videos = _videos.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val currentVideo: MutableState<Healings?> = mutableStateOf(null)
    val isPlayerLoading = mutableStateOf(false)
    val visibleTitle = mutableStateOf(true)


    init {
        player.apply {
            createFakeList()
            prepare()
        }
    }

    private fun createFakeList() {
        // Create a mutable list to store the fake video items

        val startingVideos = mutableListOf<Healings>()


        startingVideos.add(
                Healings(
                    id =45,
                    message ="A CRIPPLE HAS WALKED\t!A CRIPPLE HAS WALKED!!",
                    video ="1686404081.mp4",
                    vidlink ="https://eu2.contabostorage.com/77fef5a468e44fc8a9837b4ca1a047e4:videos/videos/1686404081.mp4",
                    time ="1686404081",
                )
            )

        savedStateHandle["videoUris"] = startingVideos
        currentVideo.value = startingVideos.first()
        player.addMediaItems(startingVideos.map { MediaItem.fromUri(it.vidlink .toUri())})
    }
    fun playVideo(uri: Uri) {
        player.setMediaItem(
            MediaItem.fromUri(uri)
        )
        currentVideo.value = videos.value.find { it.vidlink.toUri() == uri } ?: return
    }

    override fun onCleared() {
        super.onCleared()
        player.apply {
            release()
        }
    }
}