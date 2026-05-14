package devilstudio.com.farmerfriend

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import java.util.Locale

class SellerListActivity : AppCompatActivity() {

    private lateinit var sellerSearchEditText: EditText
    private lateinit var sellerCountText: TextView
    private lateinit var sellerContainer: LinearLayout

    private val allSellers = SellerRepository.getAllSellers()
    private var searchQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_list)

        sellerSearchEditText = findViewById(R.id.sellerSearchEditText)
        sellerCountText = findViewById(R.id.sellerCountText)
        sellerContainer = findViewById(R.id.sellerContainer)

        sellerSearchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchQuery = s.toString().trim()
                loadSellers()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        loadSellers()
    }

    private fun loadSellers() {
        sellerContainer.removeAllViews()

        var filteredSellers = allSellers

        if (searchQuery.isNotEmpty()) {
            val query = searchQuery.lowercase(Locale.getDefault())

            filteredSellers = ArrayList(
                allSellers.filter {
                    it.name.lowercase(Locale.getDefault()).contains(query) ||
                            it.city.lowercase(Locale.getDefault()).contains(query) ||
                            it.district.lowercase(Locale.getDefault()).contains(query) ||
                            it.address.lowercase(Locale.getDefault()).contains(query) ||
                            it.specialization.lowercase(Locale.getDefault()).contains(query) ||
                            it.phone.lowercase(Locale.getDefault()).contains(query) ||
                            it.email.lowercase(Locale.getDefault()).contains(query)
                }
            )
        }

        sellerCountText.text = "Gösterilen Satıcı Sayısı: ${filteredSellers.size}"

        if (filteredSellers.isEmpty()) {
            val emptyText = TextView(this)
            emptyText.text = "Aramanıza uygun satıcı bulunamadı."
            emptyText.textSize = 16f
            emptyText.setTextColor(Color.DKGRAY)
            emptyText.setPadding(12, 24, 12, 24)
            sellerContainer.addView(emptyText)
            return
        }

        for (seller in filteredSellers) {
            addSellerCard(seller)
        }
    }

    private fun addSellerCard(seller: Seller) {
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

        val nameText = TextView(this)
        nameText.text = seller.name
        nameText.textSize = 19f
        nameText.setTextColor(Color.rgb(20, 90, 45))
        nameText.setTypeface(null, android.graphics.Typeface.BOLD)

        val locationText = TextView(this)
        locationText.text = "Konum: ${seller.city} / ${seller.district}"
        locationText.textSize = 14f
        locationText.setTextColor(Color.DKGRAY)
        locationText.setPadding(0, 8, 0, 0)

        val phoneText = TextView(this)
        phoneText.text = "Telefon: ${seller.phone}"
        phoneText.textSize = 14f
        phoneText.setTextColor(Color.DKGRAY)
        phoneText.setPadding(0, 6, 0, 0)

        val emailText = TextView(this)
        emailText.text = "E-posta: ${seller.email}"
        emailText.textSize = 14f
        emailText.setTextColor(Color.DKGRAY)
        emailText.setPadding(0, 6, 0, 0)

        val addressText = TextView(this)
        addressText.text = "Adres: ${seller.address}"
        addressText.textSize = 14f
        addressText.setTextColor(Color.GRAY)
        addressText.setPadding(0, 6, 0, 0)

        val specializationText = TextView(this)
        specializationText.text = "Uzmanlık: ${seller.specialization}"
        specializationText.textSize = 14f
        specializationText.setTextColor(Color.GRAY)
        specializationText.setPadding(0, 6, 0, 12)

        val callButton = Button(this)
        callButton.text = "Satıcıyı Ara"
        callButton.textSize = 14f
        callButton.setTextColor(Color.WHITE)
        callButton.setBackgroundResource(R.drawable.bg_button_green)

        callButton.setOnClickListener {
            try {
                val phoneNumber = seller.phone.replace(" ", "")
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$phoneNumber")
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Arama ekranı açılamadı", Toast.LENGTH_SHORT).show()
            }
        }

        val emailButton = Button(this)
        emailButton.text = "E-posta Gönder"
        emailButton.textSize = 14f
        emailButton.setTextColor(Color.WHITE)
        emailButton.setBackgroundResource(R.drawable.bg_button_green)

        emailButton.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:${seller.email}")
                intent.putExtra(Intent.EXTRA_SUBJECT, "Bitki Hastalık Tespit Sistemi - Ürün Bilgisi")
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "E-posta uygulaması açılamadı", Toast.LENGTH_SHORT).show()
            }
        }

        card.addView(nameText)
        card.addView(locationText)
        card.addView(phoneText)
        card.addView(emailText)
        card.addView(addressText)
        card.addView(specializationText)
        card.addView(callButton)
        card.addView(emailButton)

        sellerContainer.addView(card)
    }
}