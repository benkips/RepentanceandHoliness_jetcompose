package www.digitalexperts.church_traker.Presentation.Dashboard

import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import www.digitalexperts.church_traker.R
import java.net.URLDecoder

@Composable
fun CommonWebinfo(urls:String){
    var mystringUrls:String=URLDecoder.decode(urls);
    val visibility = remember { mutableStateOf(false)}

    Log.d("Webinfo", "Webinfo: " + mystringUrls)

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
        color = Color.White
    ) {
        Box (
            Modifier.fillMaxSize()
        ){
            Column (
                /*horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                     .fillMaxSize()*/
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()

            ) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = {
                        WebView(it).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                        }
                        WebviewHolder(context = it).apply {

                            var webViewClient: WebViewClient = object : WebViewClient() {
                                override fun onReceivedError(
                                    view: WebView?,
                                    errorCode: Int,
                                    description: String?,
                                    failingUrl: String?
                                ) {
                                    super.onReceivedError(view, errorCode, description, failingUrl)
                                    visibility.value = false
                                    mystringUrls = "file:///android_asset/android/errorpage.html";
                                    webview.loadUrl(mystringUrls )

                                }

                                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                                    super.onPageStarted(view, url, favicon)
                                    visibility.value = true

                                }

                                override fun onPageFinished(view: WebView?, url: String?) {
                                    super.onPageFinished(view, url)
                                    visibility.value = false
                                }

                            }
                            webview.webViewClient = webViewClient
                            loadUrl(mystringUrls)


                        }.webview
                    },
                    update = {
                    })

            }
        }
    }
}