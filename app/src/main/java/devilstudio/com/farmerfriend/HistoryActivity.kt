package devilstudio.com.farmerfriend

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import org.json.JSONArray
import java.io.File

class HistoryActivity : AppCompatActivity() {

    private lateinit var historyContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        historyContainer = findViewById(R.id.historyContainer)

        loadHistory()

        val navHome = findViewById<TextView>(R.id.navHome)
        val navHistory = findViewById<TextView>(R.id.navHistory)
        val navAbout = findViewById<TextView>(R.id.navAbout)

        navHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        navHistory.setOnClickListener {
            Toast.makeText(this, "Zaten geçmiş ekranındasınız", Toast.LENGTH_SHORT).show()
        }

        navAbout.setOnClickListener {
            Toast.makeText(
                this,
                "Bitki hastalık tespit sistemi, yaprak görüntüsünden hastalık tahmini yapar.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun loadHistory() {
        historyContainer.removeAllViews()

        val prefs = getSharedPreferences("prediction_history", MODE_PRIVATE)
        val data = prefs.getString("items", "[]") ?: "[]"

        val array = JSONArray(data)

        if (array.length() == 0) {
            showEmptyMessage()
            return
        }

        for (i in 0 until array.length()) {
            val item = array.getJSONObject(i)

            val imagePath = item.optString("imagePath", "")
            val result = item.optString("result", "Bilinmeyen sonuç")
            val confidence = item.optString("confidence", "%0")
            val date = item.optString("date", "")

            addHistoryCard(imagePath, result, confidence, date)
        }
    }

    private fun showEmptyMessage() {
        val emptyText = TextView(this)
        emptyText.text = "Henüz analiz geçmişi bulunmuyor.\nAna sayfadan fotoğraf çekerek analiz yapabilirsiniz."
        emptyText.textSize = 15f
        emptyText.setTextColor(Color.parseColor("#6B7280"))
        emptyText.gravity = Gravity.CENTER
        emptyText.setPadding(20, 80, 20, 20)

        historyContainer.addView(
            emptyText,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
    }

    private fun addHistoryCard(
        imagePath: String,
        result: String,
        confidence: String,
        date: String
    ) {
        val card = LinearLayout(this)
        card.orientation = LinearLayout.HORIZONTAL
        card.setPadding(16, 16, 16, 16)
        card.setBackgroundResource(R.drawable.bg_card_white)
        card.gravity = Gravity.CENTER_VERTICAL
        card.elevation = 5f

        val cardParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        cardParams.setMargins(0, 0, 0, 16)
        card.layoutParams = cardParams

        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setBackgroundResource(R.drawable.bg_image_box)

        val imageParams = LinearLayout.LayoutParams(92, 92)
        imageParams.setMargins(0, 0, 16, 0)
        imageView.layoutParams = imageParams

        val file = File(imagePath)
        if (file.exists()) {
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            imageView.setImageBitmap(bitmap)
        } else {
            imageView.setImageResource(android.R.drawable.ic_menu_gallery)
        }

        val textBox = LinearLayout(this)
        textBox.orientation = LinearLayout.VERTICAL
        textBox.gravity = Gravity.CENTER_VERTICAL

        val textBoxParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        )
        textBox.layoutParams = textBoxParams

        val resultText = TextView(this)
        resultText.text = result
        resultText.textSize = 16f
        resultText.setTextColor(Color.parseColor("#1F2933"))
        resultText.setTypeface(null, Typeface.BOLD)

        val confidenceText = TextView(this)
        confidenceText.text = "Güven: $confidence"
        confidenceText.textSize = 13f
        confidenceText.setTextColor(Color.parseColor("#138A36"))
        confidenceText.setPadding(0, 6, 0, 0)

        val dateText = TextView(this)
        dateText.text = date
        dateText.textSize = 12f
        dateText.setTextColor(Color.parseColor("#6B7280"))
        dateText.setPadding(0, 6, 0, 0)

        textBox.addView(resultText)
        textBox.addView(confidenceText)
        textBox.addView(dateText)

        card.addView(imageView)
        card.addView(textBox)

        historyContainer.addView(card)
    }
}