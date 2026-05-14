package devilstudio.com.farmerfriend

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class FavoriteProductsActivity : AppCompatActivity() {

    private lateinit var favoriteContainer: LinearLayout
    private lateinit var favoriteCountText: TextView

    private val allProducts = ProductRepository.getAllProducts()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_products)

        favoriteContainer = findViewById(R.id.favoriteContainer)
        favoriteCountText = findViewById(R.id.favoriteCountText)

        loadFavorites()
    }

    override fun onResume() {
        super.onResume()
        loadFavorites()
    }

    private fun loadFavorites() {
        favoriteContainer.removeAllViews()

        val favoriteIds = FavoriteProductStorage.getFavoriteIds(this)
        val favoriteProducts = allProducts.filter { favoriteIds.contains(it.id) }

        favoriteCountText.text = "Toplam Favori Ürün: ${favoriteProducts.size}"

        if (favoriteProducts.isEmpty()) {
            val emptyText = TextView(this)
            emptyText.text = "Henüz favori ürün eklenmedi."
            emptyText.textSize = 16f
            emptyText.setTextColor(Color.DKGRAY)
            emptyText.setPadding(12, 24, 12, 24)
            favoriteContainer.addView(emptyText)
            return
        }

        for (product in favoriteProducts) {
            addFavoriteProductCard(product)
        }
    }

    private fun addFavoriteProductCard(product: Product) {
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

        val removeButton = Button(this)
        removeButton.text = "Favorilerden Çıkar"
        removeButton.setTextColor(Color.WHITE)
        removeButton.setBackgroundResource(R.drawable.bg_button_green)

        removeButton.setOnClickListener {
            FavoriteProductStorage.removeFavorite(this, product.id)
            Toast.makeText(this, "Ürün favorilerden çıkarıldı", Toast.LENGTH_SHORT).show()
            loadFavorites()
        }

        val orderButton = Button(this)
        orderButton.text = "Sipariş Talebi Oluştur"
        orderButton.setTextColor(Color.WHITE)
        orderButton.setBackgroundResource(R.drawable.bg_button_green)

        orderButton.setOnClickListener {
            val intent = Intent(this, OrderRequestActivity::class.java)
            intent.putExtra("productName", product.name)
            intent.putExtra("productCategory", product.category)
            intent.putExtra("productPrice", product.price)
            intent.putExtra("diseaseName", product.diseaseName)
            startActivity(intent)
        }

        card.addView(productImage)
        card.addView(nameText)
        card.addView(categoryText)
        card.addView(priceText)
        card.addView(diseaseText)
        card.addView(removeButton)
        card.addView(orderButton)

        favoriteContainer.addView(card)
    }
}