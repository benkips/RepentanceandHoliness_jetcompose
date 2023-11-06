package www.digitalexperts.church_traker.Viewmodels

//import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import www.digitalexperts.church_traker.Data.models.Churches
import www.digitalexperts.church_traker.Data.models.ChurchesItem
import www.digitalexperts.church_traker.Network.Resource
import www.digitalexperts.church_traker.Repo.Repostuff

import javax.inject.Inject

@HiltViewModel
class Churchviewmodel @Inject constructor(private  val repostuff: Repostuff) :ViewModel(){

    private val searchstring = MutableLiveData<String>()
    private val _isUpdating = MutableStateFlow<String?>(null)
    val isUpdating: StateFlow<String?> = _isUpdating

    val id = MutableStateFlow("")
    val name = MutableStateFlow("")
    val location = MutableStateFlow("")
    val photo = MutableStateFlow("")



    val result = searchstring.switchMap {
        liveData<Resource<Churches>> {
            emit(Resource.Loading)
            emit(repostuff.getSearchresults(it)) }
    }
    fun search(query:String){
        searchstring.value=query
    }



    fun setUpdating(ChurchItems: ChurchesItem?) {
        if (ChurchItems != null) {
            _isUpdating.value = ChurchItems.id
            name.value = ChurchItems.name
            location.value = ChurchItems.location
            photo.value = ChurchItems.photo
            id.value=ChurchItems.id

            /*validateInputs()*/
        } else {
            _isUpdating.value = null
            name.value = ""
            location.value = ""
            photo.value = ""

        }
    }
    /*private val _churchResponse: MutableStateFlow<Resource<Churches>>? = (null)
    val churchResponse:StateFlow<Resource<Churches>>?
        get() = _churchResponse
    fun search(
        query: String
    ) =
        viewModelScope.launch {
            _churchResponse?.value = Resource.Loading
            _churchResponse?.value = repostuff.getSearchresults(query)
        }*/

}