package www.digitalexperts.church_tracker.Presentation.Dashhboard

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import www.digitalexperts.church_tracker.Data.models.Churches
import www.digitalexperts.church_tracker.Data.models.ChurchesItem
import www.digitalexperts.church_tracker.Network.Resource
import www.digitalexperts.church_tracker.R
import www.digitalexperts.church_tracker.Util.Constants
import www.digitalexperts.church_tracker.Util.FullScreenProgressbar
import www.digitalexperts.church_tracker.Util.RetrySection
import www.digitalexperts.church_tracker.Viewmodels.Churchviewmodel

@Composable
fun MainDashboard(navController: NavController, viewModel: Churchviewmodel) {

    viewModel.search("nairobi")
    val viewAllchurchesList = viewModel.result.observeAsState()
    Log.d("MainDashboard", "MainDashboard: " + viewAllchurchesList)
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.cultured))
            ) {
                Column {
                    /*UserWelcomeStatement()
                    SearchSection(viewModel)*/
                    ChurchesListing(viewModel,navController)
                }

            }

        }
    }


}

@Composable
fun SearchSection(viewModel: Churchviewmodel) {
    val searchTextState = remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 12.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        BasicTextField(
            value = searchTextState.value,
            onValueChange = {
                searchTextState.value = it
            },
            singleLine = true,
            textStyle = TextStyle(fontSize = 15.sp, color = colorResource(id = R.color.gray)),
            cursorBrush = SolidColor(Color.Black),
            modifier = Modifier
                .background(Color.Transparent)
                .padding(start = 4.dp, end = 16.dp)
                .weight(1f),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            decorationBox = { innerTextField ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = colorResource(id = R.color.cultured), shape = RoundedCornerShape(16.dp))
                        .border(
                            width = 2.dp,
                            color = colorResource(id = R.color.gray),
                            shape = RoundedCornerShape(size = 16.dp)
                        )
                        .padding(16.dp),

                    ) {

                    if (searchTextState.value.isEmpty()) {
                        Text(
                            text = "SEARCH ALTAR",
                            color = colorResource(id = R.color.gray),
                            fontSize = 15.sp
                        )

                    }
                    innerTextField()
                }


            }


        )
        IconButton(
            onClick = {
                viewModel.search(searchTextState.value)
            },
            modifier = Modifier
                .padding(start = 2.dp)
                .size(24.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_round_search_24),
                contentDescription = null,
                tint = Color.Gray
            )
        }

    }
}

@Composable
fun UserWelcomeStatement() {
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(top = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Prepare The Way ,The messiah is coming",
                color = colorResource(id = R.color.black),
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Revelation 16:15",
                color = colorResource(id = R.color.gray),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun ChurchesListing(viewModel: Churchviewmodel,navController: NavController) {
    val viewAllchurchesList = viewModel.result.observeAsState()
    Log.d("DashboardScreen", "DashboardScreen: " + viewAllchurchesList.value)
    viewAllchurchesList?.value.let {
        when (it) {
            is Resource.Failure -> {
              RetrySection(error = it.errorBody?.string().toString(), onRetry ={viewModel.search("nairobi")} )
            }

            Resource.Loading -> {
                FullScreenProgressbar()
            }

            is Resource.Success -> {
                ChurchList(it.value,navController,viewModel)
            }

            else -> {
              /*  Toast.makeText(context, "error loading".toString(), Toast.LENGTH_SHORT).show()*/
            }
        }
    }


}


@Composable
fun ChurchList(churches: ArrayList<ChurchesItem>,navController: NavController,viewModel: Churchviewmodel) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.cultured))
    ) {
        LazyColumn(
            Modifier.padding(10.dp)
        ) {
            item {  UserWelcomeStatement() }
            item {   SearchSection(viewModel)}
            items(churches) {
                ChurchItem(item = it,navController,viewModel)
            }
        }

    }
}

@Composable
fun ChurchItem(item: ChurchesItem,navController: NavController,viewModel: Churchviewmodel) {

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(top = 8.dp, end = 5.dp)
            .clickable {
                viewModel.setUpdating(item)
                navController.navigate(Constants.Screens.CHURCH_DETAIL_SCREEN + "/${item.id}")
            }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Image(
                painter = rememberImagePainter("https://repentanceandholinessinfo.com/photos/" + item.photo),
                contentDescription = null,
                modifier = Modifier.size(130.dp),
                contentScale = ContentScale.FillBounds
            )

            Column(Modifier.padding(start = 5.dp)) {
                Text(
                    text = item.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Row {
                    Text(
                        text = "Location: ",
                        fontWeight = FontWeight.SemiBold,
                         color = colorResource(id = R.color.gray),
                    )
                    Text(text = item.location)
                }
            }


        }
    }
}