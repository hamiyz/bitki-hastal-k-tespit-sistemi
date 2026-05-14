package devilstudio.com.farmerfriend

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class AdminOrderActivity : AppCompatActivity() {

    private lateinit var orderContainer: LinearLayout
    private lateinit var clearOrdersButton: Button
    private lateinit var orderCountText: TextView

    private var sellerId: Int = 0
    private var sellerName: String = ""

    private var orderItems = ArrayList<Pair<Int, OrderRequest>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_order)

        sellerId = intent.getIntExtra("sellerId", 0)
        sellerName = intent.getStringExtra("sellerName") ?: "Satıcı"

        orderContainer = findViewById(R.id.orderContainer)
        clearOrdersButton = findViewById(R.id.clearOrdersButton)
        orderCountText = findViewById(R.id.orderCountText)

        loadOrders()

        clearOrdersButton.setOnClickListener {
            if (sellerId > 0) {
                Toast.makeText(
                    this,
                    "Satıcı panelinde tüm siparişleri temizleme kapalıdır",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                OrderStorage.clearOrders(this)
                Toast.makeText(this, "Tüm sipariş talepleri temizlendi", Toast.LENGTH_SHORT).show()
                loadOrders()
            }
        }
    }

    private fun loadOrders() {
        orderContainer.removeAllViews()
        orderItems.clear()

        val allOrders = OrderStorage.getOrders(this)

        val allOrderItems = ArrayList<Pair<Int, OrderRequest>>()

        for (i in 0 until allOrders.size) {
            allOrderItems.add(Pair(i, allOrders[i]))
        }

        orderItems = if (sellerId > 0) {
            ArrayList(allOrderItems.filter { it.second.sellerId == sellerId })
        } else {
            allOrderItems
        }

        orderCountText.text = if (sellerId > 0) {
            "$sellerName Siparişleri: ${orderItems.size}"
        } else {
            "Toplam Sipariş Talebi: ${orderItems.size}"
        }

        if (orderItems.isEmpty()) {
            val emptyText = TextView(this)
            emptyText.text = if (sellerId > 0) {
                "Bu satıcıya ait sipariş talebi bulunmuyor."
            } else {
                "Henüz sipariş talebi bulunmuyor."
            }
            emptyText.textSize = 16f
            emptyText.setTextColor(Color.DKGRAY)
            emptyText.setPadding(12, 24, 12, 24)
            orderContainer.addView(emptyText)
            return
        }

        for (i in orderItems.size - 1 downTo 0) {
            val originalIndex = orderItems[i].first
            val order = orderItems[i].second
            addOrderCard(order, originalIndex)
        }
    }

    private fun addOrderCard(order: OrderRequest, orderIndex: Int) {
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
        statusText.text = "Durum: ${order.status}"
        statusText.textSize = 15f
        statusText.setTextColor(getStatusColor(order.status))
        statusText.setTypeface(null, android.graphics.Typeface.BOLD)
        statusText.setPadding(0, 8, 0, 8)

        val sellerText = TextView(this)
        sellerText.text = "Satıcı: ${if (order.sellerName.isEmpty()) sellerName else order.sellerName}"
        sellerText.textSize = 14f
        sellerText.setTextColor(Color.rgb(20, 90, 45))
        sellerText.setPadding(0, 4, 0, 0)

        val customerText = TextView(this)
        customerText.text = "Müşteri: ${order.fullName}"
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

        val noteText = TextView(this)
        noteText.text = "Not: ${if (order.note.isEmpty()) "Yok" else order.note}"
        noteText.textSize = 14f
        noteText.setTextColor(Color.GRAY)
        noteText.setPadding(0, 4, 0, 0)

        val dateText = TextView(this)
        dateText.text = "Tarih: ${order.date}"
        dateText.textSize = 13f
        dateText.setTextColor(Color.GRAY)
        dateText.setPadding(0, 4, 0, 12)

        val buttonRow1 = LinearLayout(this)
        buttonRow1.orientation = LinearLayout.HORIZONTAL

        val buttonRow2 = LinearLayout(this)
        buttonRow2.orientation = LinearLayout.HORIZONTAL

        val pendingButton = createStatusButton("Beklemede")
        val contactedButton = createStatusButton("İletişime Geçildi")
        val completedButton = createStatusButton("Tamamlandı")
        val canceledButton = createStatusButton("İptal Edildi")

        pendingButton.setOnClickListener {
            updateStatus(orderIndex, "Beklemede")
        }

        contactedButton.setOnClickListener {
            updateStatus(orderIndex, "İletişime Geçildi")
        }

        completedButton.setOnClickListener {
            updateStatus(orderIndex, "Tamamlandı")
        }

        canceledButton.setOnClickListener {
            updateStatus(orderIndex, "İptal Edildi")
        }

        buttonRow1.addView(pendingButton)
        buttonRow1.addView(contactedButton)

        buttonRow2.addView(completedButton)
        buttonRow2.addView(canceledButton)

        card.addView(productText)
        card.addView(statusText)
        card.addView(sellerText)
        card.addView(customerText)
        card.addView(phoneText)
        card.addView(locationText)
        card.addView(diseaseText)
        card.addView(categoryText)
        card.addView(priceText)
        card.addView(noteText)
        card.addView(dateText)
        card.addView(buttonRow1)
        card.addView(buttonRow2)

        orderContainer.addView(card)
    }

    private fun createStatusButton(text: String): Button {
        val button = Button(this)
        button.text = text
        button.textSize = 12f
        button.setTextColor(Color.WHITE)
        button.setBackgroundResource(R.drawable.bg_button_green)

        val params = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        )
        params.setMargins(4, 6, 4, 6)
        button.layoutParams = params

        return button
    }

    private fun updateStatus(orderIndex: Int, newStatus: String) {
        OrderStorage.updateOrderStatus(this, orderIndex, newStatus)
        Toast.makeText(this, "Sipariş durumu güncellendi: $newStatus", Toast.LENGTH_SHORT).show()
        loadOrders()
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