package www.digitalexperts.church_traker.Presentation.Videoscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import www.digitalexperts.church_traker.models.Healings

@Composable
fun VideoCard( Healings: Healings, onClick: () -> Unit) {

   /* val color = if (isCurrentPlaying) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        Color.White
    }*/

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),

        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .clickable { onClick.invoke() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .padding(horizontal = 14.dp)
                    .weight(7f)
            ) {
                Text(Healings.video, fontWeight = FontWeight.SemiBold, fontSize = 17.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Cool Description",
                    maxLines = 1,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

     /*       if (isCurrentPlaying) {
                Column(Modifier.weight(3f)) {
                    Text(
                        text = "Now Playing...",
                        color = Color.Red,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
                            .padding(8.dp),
                    )
                }
            }*/
        }
    }
}