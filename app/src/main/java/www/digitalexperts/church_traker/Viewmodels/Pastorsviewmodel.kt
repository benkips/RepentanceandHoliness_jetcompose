package www.digitalexperts.church_traker.Viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import www.digitalexperts.church_traker.Network.Resource
import www.digitalexperts.church_traker.Repo.Repostuff
import www.digitalexperts.church_traker.models.Pastors
import www.digitalexperts.church_traker.models.PastorsItem
import javax.inject.Inject

@HiltViewModel
class Pastorsviewmodel @Inject constructor(private  val repostuff: Repostuff) : ViewModel(){
    private val _isUpdating = MutableStateFlow<String?>(null)
    val isUpdating: StateFlow<String?> = _isUpdating
    val name = MutableStateFlow("")
    val event = MutableStateFlow("")
    val phone = MutableStateFlow("")

    private val _pastorsResponse = MutableStateFlow<Resource<Pastors>?>(null)
    val pastorsResponse: StateFlow<Resource<Pastors>?> = _pastorsResponse


    fun getPastors(
        query: String
    ) =
        viewModelScope.launch {
            _pastorsResponse.value = Resource.Loading
            _pastorsResponse.value = repostuff.getpastors(query)
        }


    fun setUpdating(pastorsItem: PastorsItem?) {
        if (pastorsItem != null) {
            _isUpdating.value =pastorsItem.name
            name.value = pastorsItem.name
            event.value = pastorsItem.event
            phone.value = pastorsItem.phone

            /*validateInputs()*/
        } else {
            _isUpdating.value = null
            name.value = ""
            event.value = ""
            phone.value = ""

        }
    }


}