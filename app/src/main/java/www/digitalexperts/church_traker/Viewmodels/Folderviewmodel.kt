package www.digitalexperts.church_traker.Viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import www.digitalexperts.church_traker.Network.Resource
import www.digitalexperts.church_traker.Repo.Repostuff
import www.digitalexperts.church_traker.models.Folderz
import www.digitalexperts.church_traker.models.FolderzItem
import javax.inject.Inject

@HiltViewModel
class Folderviewmodel @Inject constructor(private  val repostuff: Repostuff) : ViewModel(){

    val foldername = MutableStateFlow("")

    private val _folderResponse = MutableStateFlow<Resource<Folderz>?>(null)
    val foldersResponse: StateFlow<Resource<Folderz>?> = _folderResponse

    fun getTeachings(
    ) =
        viewModelScope.launch {
            _folderResponse.value = Resource.Loading
            _folderResponse.value = repostuff.getteachingfolders()
        }


    fun setUpdating(folderzItem: FolderzItem?) {
        if (folderzItem != null) {
            foldername.value=folderzItem.folder
        } else {
            foldername.value= ""
        }
    }

}