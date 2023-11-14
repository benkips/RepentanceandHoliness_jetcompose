package www.digitalexperts.church_traker.Repo

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import www.digitalexperts.church_traker.Network.ApiInterface
import www.digitalexperts.church_traker.Paging.HealingPagSource
import javax.inject.Inject

class Repostuff @Inject constructor (private val apiInterface: ApiInterface):Baserepository(){

    suspend fun getSearchresults(query: String) =
        safeApiCall{apiInterface.searchresults(query)}

    suspend fun  getteachingfolders()= safeApiCall {
        apiInterface.getfolders("teachings") }

    suspend fun  getteachingpdfs()= safeApiCall {
            apiInterface.getfolders("magazines") }
    suspend fun  getcontents(x:String,y:String)=safeApiCall {
      apiInterface.getpdfitems(x,y)
    }
    suspend fun getpastors(x:String)=safeApiCall {
    Log.d("repo", "entry: ")
        apiInterface.getingpastors(x)

    }

    fun gethealingz() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { HealingPagSource(apiInterface) }
        ).flow
}