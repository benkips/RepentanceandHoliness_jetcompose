package www.digitalexperts.church_traker.Network

import retrofit2.http.*
import www.digitalexperts.church_traker.Data.models.*
import www.digitalexperts.church_traker.models.Folderz
import www.digitalexperts.church_traker.models.Heal
import www.digitalexperts.church_traker.models.Pastors

interface ApiInterface {
    companion object{
        const val BASE_URL="https://repentanceandholinessinfo.com/"
    }
    //search churches
    @POST("search.php")
    @FormUrlEncoded
    suspend  fun searchresults(@Field("search") q: String?): Churches

    //getting teaching folders
    @POST("getfolders.php")
    @FormUrlEncoded
    suspend fun  getfolders(@Field("folder") q:String?): Folderz

    //geting contents
    @POST("folderitems.php")
    @FormUrlEncoded
    suspend fun  getpdfitems(@Field("table") x:String?,@Field("folder")q:String?):Pdfdata

    //getting pastors
    @POST("events.php")
    @FormUrlEncoded
    suspend fun  getingpastors(@Field("cid") x:String?): Pastors

  //getting healings
    @POST()
    @FormUrlEncoded
    suspend fun  gethealings(@Url url : String,@Field("pg") x: Int?,@Field("count")q: Int?): Heal


}