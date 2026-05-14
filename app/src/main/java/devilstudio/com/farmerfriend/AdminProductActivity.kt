package devilstudio.com.farmerfriend

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class AdminProductActivity : AppCompatActivity() {

    private lateinit var adminProductCountText: TextView
    private lateinit var adminProductContainer: LinearLayout

    private var sellerId: Int = 0
    private var sellerName: String = ""
    private var sellerCity: String = ""
    private var sellerDistrict: String = ""
    private var sellerPhone: String = ""

    private var productList = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_product)

        sellerId = intent.getIntExtra("sellerId", 0)
        sellerName = intent.getStringExtra("sellerName") ?: "Satıcı"
        sellerCity = intent.getStringExtra("sellerCity") ?: ""
        sellerDistrict = intent.getStringExtra("sellerDistrict") ?: ""
        sellerPhone = intent.getStringExtra("sellerPhone") ?: ""

        adminProductCountText = findViewById(R.id.adminProductCountText)
        adminProductContainer = findViewById(R.id.adminProductContainer)

        val allProducts = ProductRepository.getAllProducts()

        productList = if (sellerId > 0) {
            ArrayList(allProducts.filter { it.sellerId == sellerId })
        } else {
            allProducts
        }

        loadProducts()
    }

    private fun loadProducts() {
        adminProductContainer.removeAllViews()

        adminProductCountText.text = "$sellerName Ürün Sayısı: ${productList.size}"

        if (productList.isEmpty()) {
            val emptyText = TextView(this)
            emptyText.text = "Bu satıcıya ait ürün bulunmuyor."
            emptyText.textSize = 16f
            emptyText.setTextColor(Color.DKGRAY)
            emptyText.setPadding(12, 24, 12, 24)
            adminProductContainer.addView(emptyText)
            return
        }

        for (product in productList) {
            addProductCard(product)
        }
    }

    private fun addProductCard(product: Product) {
        val card = LinearLayout(this)
        card.orientation = LinearLayout.VERTICAL
        card.setPadding(22, 18, 22, 18)
        card.setBackgroundResource(R.drawable.bg_card_white)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 0, 16)
        card.layoutParams = params

        val productImage = ImageView(this)
        productImage.setImageResource(product.imageResId)
        productImage.scaleType = ImageView.ScaleType.CENTER_CROP

        val imageParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            260
        )
        imageParams.setMargins(0, 0, 0, 12)
        productImage.layoutParams = imageParams

        val nameText = TextView(this)
        nameText.text = "Ürün: ${product.name}"
        nameText.textSize = 17f
        nameText.setTextColor(Color.rgb(20, 90, 45))
        nameText.setTypeface(null, android.graphics.Typeface.BOLD)

        val categoryText = TextView(this)
        categoryText.text = "Kategori: ${product.category}"
        categoryText.textSize = 14f
        categoryText.setTextColor(Color.DKGRAY)
        categoryText.setPadding(0, 6, 0, 0)

        val priceText = TextView(this)
        priceText.text = "Fiyat: ${product.price}"
        priceText.textSize = 14f
        priceText.setTextColor(Color.rgb(198, 40, 40))
        priceText.setPadding(0, 6, 0, 0)

        val diseaseText = TextView(this)
        diseaseText.text = "Hastalık: ${product.diseaseName}"
        diseaseText.textSize = 14f
        diseaseText.setTextColor(Color.DKGRAY)
        diseaseText.setPadding(0, 6, 0, 0)

        val purposeText = TextView(this)
        purposeText.text = "Kullanım amacı: ${product.purpose}"
        purposeText.textSize = 14f
        purposeText.setTextColor(Color.GRAY)
        purposeText.setPadding(0, 6, 0, 0)

        val detailText = TextView(this)
        detailText.text = "Detay: ${product.detail}"
        detailText.textSize = 14f
        detailText.setTextColor(Color.GRAY)
        detailText.setPadding(0, 6, 0, 0)

        val sellerText = TextView(this)
        sellerText.text = "Satıcı: $sellerName\nKonum: $sellerCity / $sellerDistrict\nTelefon: $sellerPhone"
        sellerText.textSize = 14f
        sellerText.setTextColor(Color.rgb(20, 90, 45))
        sellerText.setPadding(0, 8, 0, 0)

        card.addView(productImage)
        card.addView(nameText)
        card.addView(categoryText)
        card.addView(priceText)
        card.addView(diseaseText)
        card.addView(purposeText)
        card.addView(detailText)
        card.addView(sellerText)

        adminProductContainer.addView(card)
    }
}