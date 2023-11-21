package www.digitalexperts.church_traker.Presentation.Dashboard

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import www.digitalexperts.church_traker.Network.Resource
import www.digitalexperts.church_traker.R
import www.digitalexperts.church_traker.Util.FullScreenProgressbar
import www.digitalexperts.church_traker.Util.RetrySection
import www.digitalexperts.church_traker.Viewmodels.Churchviewmodel
import www.digitalexperts.church_traker.Viewmodels.Pastorsviewmodel

@Composable
fun ChurchDetailScreen(navController: NavController, viewModel: Churchviewmodel,viewModel2: Pastorsviewmodel) {
        val name = viewModel.name.collectAsState()
        val address = viewModel.location.collectAsState()
        val photo = viewModel.photo.collectAsState()
        val id = viewModel.id.collectAsState()

        val isUpdating = viewModel.isUpdating.collectAsState()
        val context = LocalContext.current





        LaunchedEffect(key1 =id.value ){
                viewModel2.getPastors(id.value)
        }

        val viewPastorPerChurch = viewModel2.pastorsResponse?.collectAsState()
        Log.d("PastorsDetail", "PastorsDetail: " + viewPastorPerChurch)
        viewPastorPerChurch?.value.let {
                when (it) {
                        is Resource.Failure -> {
                                RetrySection(error = it.errorBody!!, onRetry ={ viewModel2.getPastors(id.value)})
                        }

                        Resource.Loading -> {
                                FullScreenProgressbar()
                        }

                        is Resource.Success -> {

                                viewModel2.setUpdating(it.value[0])
                        }

                        else -> {
                                /*  Toast.makeText(context, "error loading".toString(), Toast.LENGTH_SHORT).show()*/
                        }
                }
        }

        //pastorsdetails
        val pastorsname = viewModel2.name.collectAsState()
        val phoneno = viewModel2.phone.collectAsState()



        Surface (
               modifier = Modifier
                       .fillMaxSize()
                       .padding(vertical = 10.dp, horizontal = 8.dp)
        ){
                Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                                .verticalScroll(rememberScrollState())

                ){
                        Text(text ="${name.value}",
                                fontSize= 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(1.dp)
                        )

                        Image(
                                painter = rememberImagePainter("https://repentanceandholinessinfo.com/photos/" + photo.value),
                                contentDescription = null,
                                modifier = Modifier
                                        .fillMaxWidth(1f)
                                        .height(300.dp),
                                contentScale = ContentScale.FillWidth
                        )
                        Row {

                                Icon(
                                        painter = painterResource(id = R.drawable.baseline_people_24),
                                        contentDescription = null,
                                        tint = Color.Gray,
                                        modifier = Modifier.padding(end = 1.dp)
                                )
                                Text(text ="PASTORS",
                                        fontSize= 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(1.dp))
                                

                        }
                        Text(text ="${pastorsname.value}",
                                fontSize= 15.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(2.dp)
                        )
                        Column(modifier = Modifier
                                .padding(vertical = 5.dp, horizontal = 1.dp)){
                                Button(
                                        onClick = {

                                                if (!phoneno.value.isEmpty()) {
                                                        val phn=phoneno.value
                                                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+254$phn"))
                                                        startActivity(context, intent, null)
                                                }
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.fillMaxWidth(1f),
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD81B60))
                                ) {
                                        Text(
                                                text = "Call Pastor",
                                                color= Color.White,
                                                modifier = Modifier.padding(6.dp)

                                        )

                                }
                        }

                }

        }
}