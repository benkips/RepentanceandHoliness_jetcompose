package www.digitalexperts.church_traker.Viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import www.digitalexperts.church_traker.Data.models.Pdfdata
import www.digitalexperts.church_traker.Network.Resource
import www.digitalexperts.church_traker.Repo.Repostuff
import javax.inject.Inject

@HiltViewModel
class Contentviewmodel @Inject constructor(private  val repostuff: Repostuff) : ViewModel() {


    private val _contentResponse = MutableStateFlow<Resource<Pdfdata>?>(null)
    val contentResponse: StateFlow<Resource<Pdfdata>?> = _contentResponse
    fun getcontentz(x:String,y:String) = viewModelScope.launch {
        _contentResponse.value = Resource.Loading
        _contentResponse.value=repostuff.getcontents(x,y)
    }
}