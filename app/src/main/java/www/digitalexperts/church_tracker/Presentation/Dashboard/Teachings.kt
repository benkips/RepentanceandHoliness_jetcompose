package www.digitalexperts.church_tracker.Presentation.Dashboard

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import www.digitalexperts.church_tracker.Network.Resource
import www.digitalexperts.church_tracker.R
import www.digitalexperts.church_tracker.Util.Constants
import www.digitalexperts.church_tracker.Util.FullScreenProgressbar
import www.digitalexperts.church_tracker.Util.RetrySection
import www.digitalexperts.church_tracker.Viewmodels.Folderviewmodel
import www.digitalexperts.church_tracker.models.FolderzItem

@Composable
fun Teachings(navController: NavController, viewModel: Folderviewmodel) {
    val foldertype = viewModel.foldername.collectAsState()
    LaunchedEffect(key1 = foldertype.value) {
        viewModel.getTeachings()
    }

    val Folderlist = viewModel.foldersResponse?.collectAsState()
    Log.d("Folderlist", "Folderlist: " + Folderlist)
    Folderlist?.value.let {
        when (it) {
            is Resource.Failure -> {
                RetrySection(
                    error = it.errorBody!!,
                    onRetry = { viewModel.getTeachings() })
            }

            Resource.Loading -> {
                FullScreenProgressbar()
            }

            is Resource.Success -> {

                FolderList(it.value, navController, viewModel)
            }

            else -> {
                /*  Toast.makeText(context, "error loading".toString(), Toast.LENGTH_SHORT).show()*/
            }
        }
    }

    //folderdetails
    //   val foldertype = viewModel.foldername.collectAsState()


}


@Composable
fun FolderList(
    folders: ArrayList<FolderzItem>,
    navController: NavController,
    viewModel: Folderviewmodel
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.cultured))
    ) {
        LazyColumn(
            Modifier.padding(10.dp)
        ) {
            items(folders) {
                TeachingCardItem(
                    navController = navController,
                    item = it,
                    R.drawable.ic_baseline_folder_special_24,
                    viewModel
                )
            }
        }

    }
}

@Composable
fun TeachingCardItem(
    navController: NavController,
    item: FolderzItem,
    imagevector: Int,
    viewModel: Folderviewmodel
) {

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(top = 8.dp, end = 5.dp)
            .clickable {
                viewModel.setUpdating(item)
                navController.navigate(Constants.Screens.WEBVIEW_SCREEN)
            }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Image(
                painter = painterResource(id = imagevector),
                contentDescription = null,
                modifier = Modifier.size(130.dp),
                contentScale = ContentScale.FillBounds
            )

            Column(Modifier.padding(start = 5.dp)) {
                Text(
                    text = item.folder,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Row {

                    Text(text = "(" + item.count.toString() + ")")
                    Text(
                        text = "items",
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.gray),
                    )
                }
            }


        }
    }
}