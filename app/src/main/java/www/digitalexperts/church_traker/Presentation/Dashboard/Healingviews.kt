package www.digitalexperts.church_traker.Presentation.Dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import www.digitalexperts.church_traker.MainActivity
import www.digitalexperts.church_traker.Presentation.Videoscreen.ShortViewCompose
import www.digitalexperts.church_traker.Viewmodels.Healingviewmodel
import www.digitalexperts.church_traker.models.Healings

@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun Healingviews(navController: NavController, viewModel: Healingviewmodel) {

    val healingvideos = viewModel.Healed.collectAsLazyPagingItems()
    val handlePagingResult = handlePagingResult(healingvideos)
    val activity = LocalContext.current as MainActivity
    if (handlePagingResult) {
        ShortViewCompose(
            activity =  activity,
            videoItemsUrl = healingvideos,
            clickItemPosition = 0
        )
    }



    /*Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {



        Spacer(modifier = Modifier.height(16.dp))



        Spacer(modifier = Modifier.height(16.dp))

        HealingsList(healingvideos, navController = navController)

    }*/

}
/*@Composable
fun HealingsList(
    Healings: LazyPagingItems<Healings>,
   navController: NavController
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
                    VideoCard(healing = Healing,navController )
                }
            }
        }
    }
}*/

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




