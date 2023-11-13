package www.digitalexperts.church_traker.Presentation.Dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState

@Composable
fun Pdfview(document:String){

    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Remote("https://repentanceandholinessinfo.com/documents/$document"),
        isZoomEnable = true,
        isAccessibleEnable = true
    )
    VerticalPDFReader(
        state = pdfState,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    )

}


