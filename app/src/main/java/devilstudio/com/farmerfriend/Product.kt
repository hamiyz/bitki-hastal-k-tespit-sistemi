package devilstudio.com.farmerfriend

data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val price: String,
    val diseaseName: String,
    val purpose: String,
    val detail: String,
    val imageResId: Int,
    val sellerId: Int
)