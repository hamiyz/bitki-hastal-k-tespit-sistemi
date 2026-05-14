package devilstudio.com.farmerfriend

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast

class AdminActivity : AppCompatActivity() {

    private lateinit var adminOrdersButton: Button
    private lateinit var adminProductsButton: Button
    private lateinit var adminDiseaseRecordsButton: Button
    private lateinit var adminDiseaseInfoButton: Button
    private var sellerId: Int = 0
    private var sellerName: String = ""
    private var sellerCity: String = ""
    private var sellerDistrict: String = ""
    private var sellerPhone: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        sellerId = intent.getIntExtra("sellerId", 0)
        sellerName = intent.getStringExtra("sellerName") ?: "Satıcı"
        sellerCity = intent.getStringExtra("sellerCity") ?: ""
        sellerDistrict = intent.getStringExtra("sellerDistrict") ?: ""
        sellerPhone = intent.getStringExtra("sellerPhone") ?: ""

        Toast.makeText(this, "Hoş geldiniz: $sellerName", Toast.LENGTH_LONG).show()

        adminOrdersButton = findViewById(R.id.adminOrdersButton)
        adminProductsButton = findViewById(R.id.adminProductsButton)
        adminDiseaseRecordsButton = findViewById(R.id.adminDiseaseRecordsButton)
        adminDiseaseInfoButton = findViewById(R.id.adminDiseaseInfoButton)

        adminOrdersButton.setOnClickListener {
            val intent = Intent(this, AdminOrderActivity::class.java)
            intent.putExtra("sellerId", sellerId)
            intent.putExtra("sellerName", sellerName)
            intent.putExtra("sellerCity", sellerCity)
            intent.putExtra("sellerDistrict", sellerDistrict)
            intent.putExtra("sellerPhone", sellerPhone)
            startActivity(intent)
        }

        adminProductsButton.setOnClickListener {
            val intent = Intent(this, AdminProductActivity::class.java)
            intent.putExtra("sellerId", sellerId)
            intent.putExtra("sellerName", sellerName)
            intent.putExtra("sellerCity", sellerCity)
            intent.putExtra("sellerDistrict", sellerDistrict)
            intent.putExtra("sellerPhone", sellerPhone)
            startActivity(intent)
        }

        adminDiseaseRecordsButton.setOnClickListener {
            val intent = Intent(this, DiseaseMapActivity::class.java)
            startActivity(intent)
        }

        adminDiseaseInfoButton.setOnClickListener {
            Toast.makeText(
                this,
                "Hastalık açıklaması düzenleme sonraki adımda eklenecek",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}