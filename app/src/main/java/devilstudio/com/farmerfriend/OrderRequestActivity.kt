package devilstudio.com.farmerfriend

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderRequestActivity : AppCompatActivity() {

    private lateinit var selectedProductText: TextView
    private lateinit var fullNameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var cityEditText: EditText
    private lateinit var districtEditText: EditText
    private lateinit var noteEditText: EditText
    private lateinit var sendRequestButton: Button

    private var productName: String = ""
    private var diseaseName: String = ""
    private var productCategory: String = ""
    private var productPrice: String = ""
    private var sellerId: Int = 0
    private var sellerName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_request)

        selectedProductText = findViewById(R.id.selectedProductText)
        fullNameEditText = findViewById(R.id.fullNameEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        cityEditText = findViewById(R.id.cityEditText)
        districtEditText = findViewById(R.id.districtEditText)
        noteEditText = findViewById(R.id.noteEditText)
        sendRequestButton = findViewById(R.id.sendRequestButton)

        productName = intent.getStringExtra("productName") ?: ""
        diseaseName = intent.getStringExtra("diseaseName") ?: ""
        productCategory = intent.getStringExtra("productCategory") ?: ""
        productPrice = intent.getStringExtra("productPrice") ?: ""
        sellerId = intent.getIntExtra("sellerId", 0)

        val seller = SellerRepository.getAllSellers().find { it.id == sellerId }
        sellerName = seller?.name ?: "Satıcı bilgisi yok"

        selectedProductText.text =
            "Ürün: $productName\nKategori: $productCategory\nFiyat: $productPrice\nSatıcı: $sellerName"

        sendRequestButton.setOnClickListener {
            sendOrderRequest()
        }
    }

    private fun sendOrderRequest() {
        val fullName = fullNameEditText.text.toString().trim()
        val phone = phoneEditText.text.toString().trim()
        val city = cityEditText.text.toString().trim()
        val district = districtEditText.text.toString().trim()
        val note = noteEditText.text.toString().trim()

        if (fullName.isEmpty()) {
            fullNameEditText.error = "Ad soyad giriniz"
            return
        }

        if (phone.isEmpty()) {
            phoneEditText.error = "Telefon giriniz"
            return
        }

        if (city.isEmpty()) {
            cityEditText.error = "İl giriniz"
            return
        }

        if (district.isEmpty()) {
            districtEditText.error = "İlçe giriniz"
            return
        }

        if (productName.isEmpty()) {
            Toast.makeText(this, "Ürün bilgisi alınamadı", Toast.LENGTH_SHORT).show()
            return
        }

        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        val deviceId = DeviceIdManager.getDeviceId(this)

        val orderRequest = OrderRequest(
            fullName = fullName,
            phone = phone,
            city = city,
            district = district,
            productName = productName,
            diseaseName = diseaseName,
            productCategory = productCategory,
            productPrice = productPrice,
            note = note,
            status = "Beklemede",
            date = currentDate,
            sellerId = sellerId,
            sellerName = sellerName,
            customerDeviceId = deviceId
        )

        OrderStorage.saveOrder(this, orderRequest)

        Toast.makeText(
            this,
            "Sipariş talebiniz başarıyla kaydedildi.\nDurum: ${orderRequest.status}",
            Toast.LENGTH_LONG
        ).show()

        finish()
    }
}