package www.digitalexperts.church_traker.Presentation.Dashboard

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.rajat.pdfviewer.PdfRendererView
import com.rajat.pdfviewer.compose.PdfRendererViewCompose
import www.digitalexperts.church_traker.R
import www.digitalexperts.church_traker.Util.DownloadFile

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun Pdfview(document:String) {
    val visibility = remember { mutableStateOf(false) }
    val contextForToast = LocalContext.current.applicationContext
    /*   val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Remote("https://repentanceandholinessinfo.com/documents/$document"),
        isZoomEnable = true,
        isAccessibleEnable = true
    )
    VerticalPDFReader(
        state = pdfState,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    )*/
    if (visibility.value){
        Dialog(
            onDismissRequest = {
                visibility.value = false
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false // experimental
            )
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Image(
                        painter = painterResource(id = R.drawable.dove),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,

                        modifier = Modifier
                            .height(200.dp)
                            .clip(shape = CircleShape),

                        )

                    Spacer(modifier = Modifier.height(20.dp))
                    //.........................Text: title
                    Text(
                        text = "Loading...",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        letterSpacing = 2.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    //.........................Text : description
                    Text(
                        text = "Please wait",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        letterSpacing = 5.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                    )
                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                }

            }
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {


        val lifecycleOwner = LocalLifecycleOwner.current
        PdfRendererViewCompose(
            url = "https://repentanceandholinessinfo.com/documents/$document",
            lifecycleOwner = lifecycleOwner,
            statusCallBack = object : PdfRendererView.StatusCallBack {
                override fun onPdfLoadStart() {
                    Log.i("statusCallBack", "onPdfLoadStart")
                    visibility.value = true
                }

                override fun onPdfLoadProgress(
                    progress: Int,
                    downloadedBytes: Long,
                    totalBytes: Long?
                ) {
                    //Download is in progress


                }

                override fun onPdfLoadSuccess(absolutePath: String) {
                    Log.i("statusCallBack", "onPdfLoadSuccess")
                    visibility.value = false
                }

                override fun onError(error: Throwable) {
                    Log.i("statusCallBack", "onError")
                }

                override fun onPageChanged(currentPage: Int, totalPage: Int) {
                    //Page change. Not require
                }
            }

        )
        Box(modifier = Modifier.fillMaxSize()){
        FloatingActionButton(
            modifier = Modifier
                .padding(bottom = 50.dp, start = 20.dp)
                .align(alignment = Alignment.BottomStart),
            backgroundColor =  Color(0xFFD81B60),
            contentColor = Color.White,
            onClick = {
                var downloadFile= DownloadFile(contextForToast)
                downloadFile.downloadFilepdf("https://repentanceandholinessinfo.com/documents/$document")
                Toast.makeText(contextForToast, "Downloading ...check your downloads", Toast.LENGTH_SHORT).show()

            }
        ) {
            Icon( painter = painterResource(R.drawable.baseline_file_download_24), contentDescription = "UP")
        }
        }

    }

}

