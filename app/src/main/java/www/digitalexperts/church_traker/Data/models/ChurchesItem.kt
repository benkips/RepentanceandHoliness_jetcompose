package www.digitalexperts.church_traker.Data.models

data class ChurchesItem(
    val id: String,
    val latitude: String,
    val location: String,
    val longitude: String,
    val name: String,
    val photo: String,
    val status:String
)