package devilstudio.com.farmerfriend

data class OrderRequest(
    val fullName: String,
    val phone: String,
    val city: String,
    val district: String,
    val productName: String,
    val diseaseName: String,
    val productCategory: String,
    val productPrice: String,
    val note: String,
    val status: String = "Beklemede",
    val date: String = "",
    val sellerId: Int = 0,
    val sellerName: String = "",
    val customerDeviceId: String = ""
)