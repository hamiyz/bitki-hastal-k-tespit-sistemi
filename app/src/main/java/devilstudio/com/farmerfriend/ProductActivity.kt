package devilstudio.com.farmerfriend

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import java.util.Locale

class ProductActivity : AppCompatActivity() {

    private lateinit var diseaseTitleText: TextView
    private lateinit var productContainer: LinearLayout

    private lateinit var allButton: Button
    private lateinit var fungicideButton: Button
    private lateinit var bactericideButton: Button
    private lateinit var fertilizerButton: Button
    private lateinit var organicButton: Button
    private lateinit var favoriteProductsButton: Button

    private lateinit var productSearchEditText: EditText
    private lateinit var productSearchInfoText: TextView
    private lateinit var productSellerListButton: Button

    private var diseaseName: String = ""
    private var selectedCategory: String = "Tümü"
    private var searchQuery: String = ""

    private val allProducts = ProductRepository.getAllProducts()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        diseaseTitleText = findViewById(R.id.diseaseTitleText)
        productContainer = findViewById(R.id.productContainer)
        productSellerListButton = findViewById(R.id.productSellerListButton)

        allButton = findViewById(R.id.allButton)
        fungicideButton = findViewById(R.id.fungicideButton)
        bactericideButton = findViewById(R.id.bactericideButton)
        fertilizerButton = findViewById(R.id.fertilizerButton)
        organicButton = findViewById(R.id.organicButton)
        favoriteProductsButton = findViewById(R.id.favoriteProductsButton)

        productSearchEditText = findViewById(R.id.productSearchEditText)
        productSearchInfoText = findViewById(R.id.productSearchInfoText)

        diseaseName = intent.getStringExtra("diseaseName") ?: ""

        if (diseaseName.isEmpty()) {
            diseaseName = "Hastalık seçilmedi"
        }

        diseaseTitleText.text = "$diseaseName için önerilen ürünler"

        showProducts()

        allButton.setOnClickListener {
            selectedCategory = "Tümü"
            showProducts()
        }

        fungicideButton.setOnClickListener {
            selectedCategory = "Fungisit"
            showProducts()
        }

        bactericideButton.setOnClickListener {
            selectedCategory = "Bakterisit"
            showProducts()
        }

        fertilizerButton.setOnClickListener {
            selectedCategory = "Yaprak Gübresi"
            showProducts()
        }

        organicButton.setOnClickListener {
            selectedCategory = "Organik Ürün"
            showProducts()
        }

        favoriteProductsButton.setOnClickListener {
            val intent = Intent(this, FavoriteProductsActivity::class.java)
            startActivity(intent)
        }

        productSellerListButton.setOnClickListener {
            val intent = Intent(this, SellerListActivity::class.java)
            startActivity(intent)
        }

        productSearchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                searchQuery = s.toString().trim()
                showProducts()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun showProducts() {
        productContainer.removeAllViews()

        var filteredProducts = allProducts.filter {
            it.diseaseName == diseaseName
        }

        if (filteredProducts.isEmpty()) {
            filteredProducts = allProducts
        }

        if (selectedCategory != "Tümü") {
            filteredProducts = filteredProducts.filter {
                it.category == selectedCategory
            }
        }

        if (searchQuery.isNotEmpty()) {
            val query = searchQuery.lowercase(Locale.getDefault())

            filteredProducts = filteredProducts.filter {
                it.name.lowercase(Locale.getDefault()).contains(query) ||
                        it.category.lowercase(Locale.getDefault()).contains(query) ||
                        it.diseaseName.lowercase(Locale.getDefault()).contains(query) ||
                        it.purpose.lowercase(Locale.getDefault()).contains(query) ||
                        it.detail.lowercase(Locale.getDefault()).contains(query)
            }
        }

        productSearchInfoText.text = "Gösterilen ürün sayısı: ${filteredProducts.size}"

        if (filteredProducts.isEmpty()) {
            val emptyText = TextView(this)
            emptyText.text = "Aramanıza uygun ürün bulunamadı."
            emptyText.textSize = 16f
            emptyText.setTextColor(Color.DKGRAY)
            emptyText.setPadding(16, 24, 16, 24)
            productContainer.addView(emptyText)
            return
        }

        for (product in filteredProducts) {
            addProductCard(product)
        }
    }

    private fun addProductCard(product: Product) {
        val card = LinearLayout(this)
        card.orientation = LinearLayout.VERTICAL
        card.setPadding(24, 20, 24, 20)
        card.setBackgroundResource(R.drawable.bg_card_white)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 0, 20)
        card.layoutParams = params

        val productImage = ImageView(this)
        productImage.setImageResource(product.imageResId)
        productImage.scaleType = ImageView.ScaleType.CENTER_CROP

        val imageParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            320
        )
        imageParams.setMargins(0, 0, 0, 16)
        productImage.layoutParams = imageParams
        productImage.setBackgroundColor(Color.WHITE)

        val nameText = TextView(this)
        nameText.text = product.name
        nameText.textSize = 18f
        nameText.setTextColor(Color.rgb(20, 90, 45))
        nameText.setTypeface(null, android.graphics.Typeface.BOLD)

        val categoryText = TextView(this)
        categoryText.text = "Kategori: ${product.category}"
        categoryText.textSize = 14f
        categoryText.setTextColor(Color.DKGRAY)
        categoryText.setPadding(0, 8, 0, 0)

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
        purposeText.setTextColor(Color.DKGRAY)
        purposeText.setPadding(0, 6, 0, 0)

        val detailText = TextView(this)
        detailText.text = "Detay: ${product.detail}"
        detailText.textSize = 14f
        detailText.setTextColor(Color.GRAY)
        detailText.setPadding(0, 6, 0, 12)

        val seller = SellerRepository.getAllSellers().find { it.id == product.sellerId }

        val sellerText = TextView(this)
        sellerText.text = if (seller != null) {
            "Satıcı: ${seller.name}\nKonum: ${seller.city} / ${seller.district}\nTelefon: ${seller.phone}"
        } else {
            "Satıcı bilgisi bulunamadı"
        }
        sellerText.textSize = 14f
        sellerText.setTextColor(Color.rgb(20, 90, 45))
        sellerText.setPadding(0, 6, 0, 12)

        val favoriteButton = Button(this)

        if (FavoriteProductStorage.isFavorite(this, product.id)) {
            favoriteButton.text = "Favorilerden Çıkar"
        } else {
            favoriteButton.text = "Favorilere Ekle"
        }

        favoriteButton.setBackgroundResource(R.drawable.bg_button_green)
        favoriteButton.setTextColor(Color.WHITE)

        val favoriteParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        favoriteParams.setMargins(0, 8, 0, 8)
        favoriteButton.layoutParams = favoriteParams

        favoriteButton.setOnClickListener {
            if (FavoriteProductStorage.isFavorite(this, product.id)) {
                FavoriteProductStorage.removeFavorite(this, product.id)
                favoriteButton.text = "Favorilere Ekle"
                Toast.makeText(this, "Ürün favorilerden çıkarıldı", Toast.LENGTH_SHORT).show()
            } else {
                FavoriteProductStorage.addFavorite(this, product.id)
                favoriteButton.text = "Favorilerden Çıkar"
                Toast.makeText(this, "Ürün favorilere eklendi", Toast.LENGTH_SHORT).show()
            }
        }

        val orderButton = Button(this)
        orderButton.text = "Sipariş Talebi Oluştur"
        orderButton.setBackgroundResource(R.drawable.bg_button_green)
        orderButton.setTextColor(Color.WHITE)

        orderButton.setOnClickListener {
            val intent = Intent(this, OrderRequestActivity::class.java)
            intent.putExtra("productName", product.name)
            intent.putExtra("productCategory", product.category)
            intent.putExtra("productPrice", product.price)
            intent.putExtra("diseaseName", product.diseaseName)
            intent.putExtra("sellerId", product.sellerId)
            startActivity(intent)
        }

        card.addView(productImage)
        card.addView(nameText)
        card.addView(categoryText)
        card.addView(priceText)
        card.addView(diseaseText)
        card.addView(purposeText)
        card.addView(detailText)
        card.addView(sellerText)
        card.addView(favoriteButton)
        card.addView(orderButton)

        productContainer.addView(card)
    }
}