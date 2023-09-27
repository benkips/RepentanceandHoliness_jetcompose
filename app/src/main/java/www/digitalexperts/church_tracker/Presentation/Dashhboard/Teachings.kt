package www.digitalexperts.church_tracker.Presentation.Dashhboard

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
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import www.digitalexperts.church_tracker.Data.models.ChurchesItem
import www.digitalexperts.church_tracker.R
import www.digitalexperts.church_tracker.Util.Constants
import www.digitalexperts.church_tracker.Viewmodels.Churchviewmodel

@Composable
fun Teachings() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.cultured))
    ) {
        LazyColumn(
            Modifier.padding(10.dp)
        ) {

            item { }
            item {  }
        }

    }
}

@Composable
fun TeachingCard(imagevector: ImageVector, title: String, itemCount:String) {

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(top = 8.dp, end = 5.dp)
            .clickable {
/*                viewModel.setUpdating(item)
                navController.navigate(Constants.Screens.CHURCH_DETAIL_SCREEN + "/${item.id}")*/
            }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Image(
                painter = rememberImagePainter(imagevector),
                contentDescription = null,
                modifier = Modifier.size(130.dp),
                contentScale = ContentScale.FillBounds
            )

            Column(Modifier.padding(start = 5.dp)) {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Row {
                    Text(
                        text = "Location: ",
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.gray),
                    )
                    Text(text = itemCount)
                }
            }


        }
    }
}