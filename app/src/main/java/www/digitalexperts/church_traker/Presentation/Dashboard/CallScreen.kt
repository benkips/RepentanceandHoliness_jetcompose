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
import www.digitalexperts.church_traker.R

@Composable
fun CallScreen() {

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
                Text(text ="PRAYER LINES CONTACTS",
                    fontSize= 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(1.dp))


            }


            Row {


                Text(text ="+254708412344\n" +
                        " +254715276091\n" +
                        " +254712944578\n",
                    fontSize= 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(1.dp))


            }

            Row {

                Icon(
                    painter = painterResource(id = R.drawable.baseline_mail_outline_24),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.padding(end = 1.dp)
                )
                Text(text ="Email Prayer Line",
                    fontSize= 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(1.dp))


            }


            Row {


                Text(text ="repentoffice@gmail.com\n" +
                        " jesusislord.fmradio@gmail.com\n" ,
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
                Text(text ="Calling and Contributing \n" +
                        "Live on Jesus is lord radio",
                    fontSize= 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(1.dp))


            }


            Row {


                Text(text =" +25477445851\n" +
                        "+25477445850 \n" ,
                    fontSize= 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(1.dp))


            }

            Row {

                Icon(
                    painter = painterResource(id = androidx.core.R.drawable.ic_call_answer_video),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.padding(end = 1.dp)
                )
                Text(text ="Sms The Radio through Text WhatsApp on",
                    fontSize= 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(1.dp))


            }


            Row {


                Text(text ="+254 727 503030" ,
                    fontSize= 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(1.dp))


            }
        }
    }
}