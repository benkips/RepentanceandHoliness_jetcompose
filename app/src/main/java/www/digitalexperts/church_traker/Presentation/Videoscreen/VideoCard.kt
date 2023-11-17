package www.digitalexperts.church_traker.Presentation.Videoscreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import www.digitalexperts.church_traker.models.Healings

@Composable
fun VideoCard( healing: Healings,navController:NavController) {

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),

        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),

    ) {
        Row(
            Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .clickable {
                    Log.d("healing.vidlink", "healing.vidlink: " + healing.video)
                   navController.navigate( "videoview/${healing.video}")
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .padding(8.dp), contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberImagePainter("http://mobile.repentanceandholinessinfo.com/static/uploads/thumb.jpeg"),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(56.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                Modifier
                    .padding(horizontal = 14.dp)
                    .weight(7f)
            ) {
                Text(healing.title, fontWeight = FontWeight.SemiBold, fontSize = 17.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Cool Description",
                    maxLines = 1,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

        }
    }
}
