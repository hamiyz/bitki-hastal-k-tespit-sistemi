package devilstudio.com.farmerfriend

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.TextView

class MyOrdersActivity : AppCompatActivity() {

    private lateinit var myOrderCountText: TextView
    private lateinit var myOrdersContainer: LinearLayout

    private var myOrders = ArrayList<OrderRequest>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_orders)

        myOrderCountText = findViewById(R.id.myOrderCountText)
        myOrdersContainer = findViewById(R.id.myOrdersContainer)

        loadMyOrders()
    }

    private fun loadMyOrders() {
        myOrdersContainer.removeAllViews()

        val deviceId = DeviceIdManager.getDeviceId(this)
        val allOrders = OrderStorage.getOrders(this)

        myOrders = ArrayList(
            allOrders.filter {
                it.customerDeviceId == deviceId || it.customerDeviceId.isEmpty()
            }
        )

        myOrderCountText.text = "Bu Cihazdaki Sipariş Sayısı: ${myOrders.size}"

        if (myOrders.isEmpty()) {
            val emptyText = TextView(this)
            emptyText.text = "Bu cihazda oluşturulmuş sipariş bulunmuyor."
            emptyText.textSize = 16f
            emptyText.setTextColor(Color.DKGRAY)
            emptyText.setPadding(12, 24, 12, 24)
            myOrdersContainer.addView(emptyText)
            return
        }

        for (i in myOrders.size - 1 downTo 0) {
            addOrderCard(myOrders[i])
        }
    }

    private fun addOrderCard(order: OrderRequest) {
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

        val productText = TextView(this)
        productText.text = "Ürün: ${order.productName}"
        productText.textSize = 17f
        productText.setTextColor(Color.rgb(20, 90, 45))
        productText.setTypeface(null, android.graphics.Typeface.BOLD)

        val statusText = TextView(this)
        statusText.text = "Sipariş Durumu: ${order.status}"
        statusText.textSize = 16f
        statusText.setTextColor(getStatusColor(order.status))
        statusText.setTypeface(null, android.graphics.Typeface.BOLD)
        statusText.setPadding(0, 8, 0, 8)

        val sellerText = TextView(this)
        sellerText.text = "Satıcı: ${if (order.sellerName.isEmpty()) "Satıcı bilgisi yok" else order.sellerName}"
        sellerText.textSize = 14f
        sellerText.setTextColor(Color.rgb(20, 90, 45))
        sellerText.setPadding(0, 4, 0, 0)

        val diseaseText = TextView(this)
        diseaseText.text = "Hastalık: ${order.diseaseName}"
        diseaseText.textSize = 14f
        diseaseText.setTextColor(Color.DKGRAY)
        diseaseText.setPadding(0, 4, 0, 0)

        val categoryText = TextView(this)
        categoryText.text = "Kategori: ${order.productCategory}"
        categoryText.textSize = 14f
        categoryText.setTextColor(Color.DKGRAY)
        categoryText.setPadding(0, 4, 0, 0)

        val priceText = TextView(this)
        priceText.text = "Fiyat: ${order.productPrice}"
        priceText.textSize = 14f
        priceText.setTextColor(Color.rgb(198, 40, 40))
        priceText.setPadding(0, 4, 0, 0)

        val customerText = TextView(this)
        customerText.text = "Ad Soyad: ${order.fullName}"
        customerText.textSize = 14f
        customerText.setTextColor(Color.DKGRAY)
        customerText.setPadding(0, 4, 0, 0)

        val phoneText = TextView(this)
        phoneText.text = "Telefon: ${order.phone}"
        phoneText.textSize = 14f
        phoneText.setTextColor(Color.DKGRAY)
        phoneText.setPadding(0, 4, 0, 0)

        val locationText = TextView(this)
        locationText.text = "Konum: ${order.city} / ${order.district}"
        locationText.textSize = 14f
        locationText.setTextColor(Color.DKGRAY)
        locationText.setPadding(0, 4, 0, 0)

        val noteText = TextView(this)
        noteText.text = "Not: ${if (order.note.isEmpty()) "Yok" else order.note}"
        noteText.textSize = 14f
        noteText.setTextColor(Color.GRAY)
        noteText.setPadding(0, 4, 0, 0)

        val dateText = TextView(this)
        dateText.text = "Sipariş Tarihi: ${order.date}"
        dateText.textSize = 13f
        dateText.setTextColor(Color.GRAY)
        dateText.setPadding(0, 4, 0, 0)

        card.addView(productText)
        card.addView(statusText)
        card.addView(sellerText)
        card.addView(diseaseText)
        card.addView(categoryText)
        card.addView(priceText)
        card.addView(customerText)
        card.addView(phoneText)
        card.addView(locationText)
        card.addView(noteText)
        card.addView(dateText)

        myOrdersContainer.addView(card)
    }

    private fun getStatusColor(status: String): Int {
        return when (status) {
            "Beklemede" -> Color.rgb(255, 152, 0)
            "İletişime Geçildi" -> Color.rgb(33, 150, 243)
            "Tamamlandı" -> Color.rgb(19, 138, 54)
            "İptal Edildi" -> Color.rgb(198, 40, 40)
            else -> Color.DKGRAY
        }
    }
}