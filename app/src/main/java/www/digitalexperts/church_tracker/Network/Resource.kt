package www.digitalexperts.church_tracker.Network

import okhttp3.ResponseBody

sealed  class Resource<out T>  {
    data class  Success<out T>(val value: T):Resource<T>()
    data class Failure(
        val isNetworkError:Boolean,
        val errorCode:Int?,
        val errorBody: String?
    ):Resource<Nothing>()
    object Loading:Resource<Nothing>()
}