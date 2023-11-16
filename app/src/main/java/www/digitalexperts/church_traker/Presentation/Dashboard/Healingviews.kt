package www.digitalexperts.church_traker.Presentation.Dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import www.digitalexperts.church_traker.Viewmodels.Healingviewmodel
import androidx.paging.compose.itemsIndexed
import www.digitalexperts.church_traker.Presentation.Videoscreen.VideoCard
import www.digitalexperts.church_traker.models.Healings
import java.lang.Math.abs

@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun Healingviews(navController: NavController, viewModel: Healingviewmodel) {

    val healingvideos = viewModel.Healed.collectAsLazyPagingItems()
 



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {



        Spacer(modifier = Modifier.height(16.dp))



        Spacer(modifier = Modifier.height(16.dp))

        HealingsList(healingvideos/*,{}*/,viewModel)

    }

}
    @Composable
    fun HealingsList(
        Healings: LazyPagingItems<Healings>,
        /*onClick: (Healings) -> Unit,*/
        viewModel: Healingviewmodel
    ) {

        val handlePagingResult = handlePagingResult(Healings)


        if (handlePagingResult) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                contentPadding = PaddingValues(all = 2.dp)
            ) {
                items(
                    count = Healings.itemCount,
                ) {
                    Healings[it]?.let { Healing ->
                        VideoCard(Healings = Healing, onClick = {   })
                    }
                }
            }
        }
    }

    @Composable
    fun handlePagingResult(articles: LazyPagingItems<Healings>): Boolean {
        val loadState = articles.loadState
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }

        return when {
            loadState.refresh is LoadState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
                false
            }

            error != null -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Ooops something went wrong")
                }
                false
            }

            else -> {
                true
            }
        }
    }




