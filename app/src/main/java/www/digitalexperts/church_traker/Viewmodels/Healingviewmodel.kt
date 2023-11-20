package www.digitalexperts.church_traker.Viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import www.digitalexperts.church_traker.Repo.Repostuff
import javax.inject.Inject


@HiltViewModel
class Healingviewmodel @Inject constructor(
    private  val repostuff: Repostuff,
    private val savedStateHandle: SavedStateHandle
    ) : ViewModel() {
    val Healed = repostuff.gethealingz()

}