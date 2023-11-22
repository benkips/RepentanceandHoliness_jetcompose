package www.digitalexperts.church_traker.Presentation.Dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun callScreen() {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp, horizontal = 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(rememberScrollState())

        ) {

            Row {

                Icon(
                    painter = painterResource(id = androidx.core.R.drawable.ic_call_answer),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.padding(end = 1.dp)
                )
                Text(text ="PRAYER LINES",
                    fontSize= 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(1.dp))


            }


            Row {

                Icon(
                    painter = painterResource(id = androidx.core.R.drawable.ic_call_answer),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.padding(end = 1.dp)
                )
                Text(text ="+254708412344\n",
                    fontSize= 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(1.dp))


            }
        }
    }
}