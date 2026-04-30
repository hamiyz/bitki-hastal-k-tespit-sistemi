package devilstudio.com.farmerfriend

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import org.json.JSONArray
import java.io.File

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scrollView = ScrollView(this)
        scrollView.setBackgroundColor(Color.parseColor("#DDEED4"))

        val container = LinearLayout(this)
        container.orientation = LinearLayout.VERTICAL
        container.setPadding(24, 30, 24, 30)

        scrollView.addView(container)
        setContentView(scrollView)

        // Başlık
        val title = TextView(this)
        title.text = "🕘 Geçmiş Analizler"
        title.textSize = 26f
        title.setTextColor(Color.parseColor("#1B5E20"))
        title.setTypeface(null, android.graphics.Typeface.BOLD)
        title.setPadding(0, 0, 0, 20)
        container.addView(title)

        // Silme butonu
        val deleteButton = Button(this)
        deleteButton.text = "🗑 Tüm Geçmişi Sil"
        deleteButton.setTextColor(Color.WHITE)
        deleteButton.setBackgroundColor(Color.parseColor("#6D4C41"))
        container.addView(deleteButton)

        deleteButton.setOnClickListener {
            android.app.AlertDialog.Builder(this)
                .setTitle("Geçmişi Sil")
                .setMessage("Tüm geçmiş kayıtları silinsin mi?")
                .setPositiveButton("Evet") { _, _ ->
                    val prefs = getSharedPreferences("prediction_history", MODE_PRIVATE)
                    prefs.edit().remove("items").apply()

                    Toast.makeText(this, "Geçmiş silindi", Toast.LENGTH_SHORT).show()
                    recreate()
                }
                .setNegativeButton("Hayır", null)
                .show()
        }

        val space = Space(this)
        space.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            24
        )
        container.addView(space)

        val prefs = getSharedPreferences("prediction_history", MODE_PRIVATE)
        val data = prefs.getString("items", "[]")
        val array = JSONArray(data)

        if (array.length() == 0) {
            val emptyText = TextView(this)
            emptyText.text = "Henüz geçmiş kaydı yok."
            emptyText.textSize = 18f
            emptyText.setTextColor(Color.parseColor("#4E6E58"))
            emptyText.gravity = Gravity.CENTER
            emptyText.setPadding(0, 80, 0, 0)
            container.addView(emptyText)
            return
        }

        for (i in array.length() - 1 downTo 0) {
            val item = array.getJSONObject(i)

            val imagePath = item.getString("imagePath")
            val result = item.getString("result")
            val confidence = item.getString("confidence")
            val date = item.getString("date")

            // Kart
            val card = LinearLayout(this)
            card.orientation = LinearLayout.HORIZONTAL
            card.setPadding(16, 16, 16, 16)
            card.setBackgroundColor(Color.parseColor("#F9FFF6"))
            card.elevation = 8f

            val cardParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            cardParams.setMargins(0, 0, 0, 18)
            card.layoutParams = cardParams

            // Fotoğraf
            val imageView = ImageView(this)
            val imageParams = LinearLayout.LayoutParams(170, 170)
            imageView.layoutParams = imageParams
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setBackgroundColor(Color.parseColor("#CFE8C9"))

            val file = File(imagePath)
            if (file.exists()) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(imagePath))
            } else {
                imageView.setImageResource(android.R.drawable.ic_menu_report_image)
            }

            // Bilgi alanı
            val infoLayout = LinearLayout(this)
            infoLayout.orientation = LinearLayout.VERTICAL
            infoLayout.setPadding(20, 0, 0, 0)

            val resultText = TextView(this)
            resultText.text = result
            resultText.textSize = 17f
            resultText.setTextColor(Color.parseColor("#1B5E20"))
            resultText.setTypeface(null, android.graphics.Typeface.BOLD)

            val confidenceText = TextView(this)
            confidenceText.text = "Güven: $confidence"
            confidenceText.textSize = 15f
            confidenceText.setTextColor(Color.parseColor("#6D4C41"))
            confidenceText.setPadding(0, 8, 0, 0)

            val dateText = TextView(this)
            dateText.text = "Tarih: $date"
            dateText.textSize = 14f
            dateText.setTextColor(Color.parseColor("#4E6E58"))
            dateText.setPadding(0, 8, 0, 0)

            infoLayout.addView(resultText)
            infoLayout.addView(confidenceText)
            infoLayout.addView(dateText)

            card.addView(imageView)
            card.addView(infoLayout)

            container.addView(card)
        }
    }
}