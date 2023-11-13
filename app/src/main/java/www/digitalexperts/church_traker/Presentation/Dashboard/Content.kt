package www.digitalexperts.church_traker.Presentation.Dashboard

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
import www.digitalexperts.church_traker.Data.models.PdfdataItem
import www.digitalexperts.church_traker.Network.Resource
import www.digitalexperts.church_traker.R
import www.digitalexperts.church_traker.Util.FullScreenProgressbar
import www.digitalexperts.church_traker.Util.RetrySection
import www.digitalexperts.church_traker.Viewmodels.Contentviewmodel
import www.digitalexperts.church_traker.Viewmodels.Pdfviewmodel


@Composable
fun Content(navController: NavController, viewModel: Contentviewmodel,viewModel2: Pdfviewmodel) {
    val foldertype = viewModel2.foldername.collectAsState()

    LaunchedEffect(key1 = foldertype.value) {
        viewModel.getcontentz("magazines",foldertype.value)
    }

    val Folderlist = viewModel.contentResponse?.collectAsState()
    Log.d("contentlist", "contentlist: " + Folderlist)
    Folderlist?.value.let {
        when (it) {
            is Resource.Failure -> {
                RetrySection(
                    error = it.errorBody!!,
                    onRetry = { viewModel.getcontentz("magazines",foldertype.value) })
            }

            Resource.Loading -> {
                FullScreenProgressbar()
            }

            is Resource.Success -> {

                ContentList(it.value, navController)
            }

            else -> {
                /*  Toast.makeText(context, "error loading".toString(), Toast.LENGTH_SHORT).show()*/
            }
        }
    }



}


@Composable
fun ContentList(
    folders: ArrayList<PdfdataItem>,
    navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.cultured))
    ) {
        LazyColumn(
            Modifier.padding(10.dp)
        ) {
            items(folders) {
                ContentCardItem(
                    navController = navController,
                    item = it,
                    R.drawable.pdficon,
                )
            }
        }

    }
}

@Composable
fun ContentCardItem(
    navController: NavController,
    item: PdfdataItem,
    imagevector: Int
) {

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(top = 8.dp, end = 5.dp)
            .clickable {
                navController.navigate( "pdfview/${item.document}")
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
                    text = item.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )

            }


        }
    }
}