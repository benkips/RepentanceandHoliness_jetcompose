package www.digitalexperts.church_traker.Repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import www.digitalexperts.church_traker.Network.Resource
import java.net.ConnectException
import java.net.UnknownHostException

abstract class Baserepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            }catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.Failure(true, throwable.code(), throwable.response()?.errorBody().toString())
                    }
                    is ConnectException -> {
                        Resource.Failure(false, null, "No internet access!")
                    }
                    is UnknownHostException -> {
                        Resource.Failure(false, null, "No internet access!")
                    }
                    else -> {
                        Resource.Failure(false, null,"OOp!something went wrong" )
                    }

                }

            }
        }
    }

}