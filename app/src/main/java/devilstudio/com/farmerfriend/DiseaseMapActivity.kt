package devilstudio.com.farmerfriend

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class DiseaseMapActivity : AppCompatActivity() {

    private lateinit var diseaseMapContainer: LinearLayout
    private lateinit var diseaseMapCountText: TextView
    private lateinit var clearDiseaseRecordsButton: Button

    private var recordList = ArrayList<DiseaseRecord>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disease_map)

        diseaseMapContainer = findViewById(R.id.diseaseMapContainer)
        diseaseMapCountText = findViewById(R.id.diseaseMapCountText)
        clearDiseaseRecordsButton = findViewById(R.id.clearDiseaseRecordsButton)

        loadRecords()

        clearDiseaseRecordsButton.setOnClickListener {
            DiseaseRecordStorage.clearRecords(this)
            Toast.makeText(this, "Bölgesel kayıtlar temizlendi", Toast.LENGTH_SHORT).show()
            loadRecords()
        }
    }

    private fun loadRecords() {
        diseaseMapContainer.removeAllViews()

        recordList = DiseaseRecordStorage.getRecords(this)

        diseaseMapCountText.text = "Toplam Hastalık Kaydı: ${recordList.size}"

        if (recordList.isEmpty()) {
            val emptyText = TextView(this)
            emptyText.text = "Henüz bölgesel hastalık kaydı bulunmuyor."
            emptyText.textSize = 16f
            emptyText.setTextColor(Color.DKGRAY)
            emptyText.setPadding(12, 24, 12, 24)
            diseaseMapContainer.addView(emptyText)
            return
        }

        addSummaryCards(recordList)

        for (i in recordList.size - 1 downTo 0) {
            addRecordCard(recordList[i])
        }
    }

    private fun addSummaryCards(records: ArrayList<DiseaseRecord>) {
        val summaryTitle = TextView(this)
        summaryTitle.text = "Bölgesel Özet"
        summaryTitle.textSize = 18f
        summaryTitle.setTextColor(Color.rgb(20, 90, 45))
        summaryTitle.setTypeface(null, android.graphics.Typeface.BOLD)
        summaryTitle.setPadding(0, 8, 0, 12)

        diseaseMapContainer.addView(summaryTitle)

        val groupedRecords = records.groupBy {
            "${it.city} - ${it.diseaseName}"
        }

        for ((title, list) in groupedRecords) {
            val summaryCard = LinearLayout(this)
            summaryCard.orientation = LinearLayout.VERTICAL
            summaryCard.setPadding(20, 16, 20, 16)
            summaryCard.setBackgroundResource(R.drawable.bg_card_white)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 0, 12)
            summaryCard.layoutParams = params

            val summaryText = TextView(this)
            summaryText.text = "$title: ${list.size} kayıt"
            summaryText.textSize = 15f
            summaryText.setTextColor(Color.DKGRAY)

            summaryCard.addView(summaryText)
            diseaseMapContainer.addView(summaryCard)
        }

        val detailTitle = TextView(this)
        detailTitle.text = "Kayıt Detayları"
        detailTitle.textSize = 18f
        detailTitle.setTextColor(Color.rgb(20, 90, 45))
        detailTitle.setTypeface(null, android.graphics.Typeface.BOLD)
        detailTitle.setPadding(0, 18, 0, 12)

        diseaseMapContainer.addView(detailTitle)
    }

    private fun addRecordCard(record: DiseaseRecord) {
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

        val diseaseText = TextView(this)
        diseaseText.text = record.diseaseName
        diseaseText.textSize = 17f
        diseaseText.setTextColor(Color.rgb(20, 90, 45))
        diseaseText.setTypeface(null, android.graphics.Typeface.BOLD)

        val locationText = TextView(this)
        locationText.text = "Konum: ${record.city} / ${record.district}"
        locationText.textSize = 14f
        locationText.setTextColor(Color.DKGRAY)
        locationText.setPadding(0, 6, 0, 0)

        val confidenceText = TextView(this)
        confidenceText.text = "Güven: ${record.confidence}"
        confidenceText.textSize = 14f
        confidenceText.setTextColor(Color.DKGRAY)
        confidenceText.setPadding(0, 6, 0, 0)

        val dateText = TextView(this)
        dateText.text = "Tarih: ${record.date}"
        dateText.textSize = 13f
        dateText.setTextColor(Color.GRAY)
        dateText.setPadding(0, 6, 0, 12)

        val detailButton = Button(this)
        detailButton.text = "Detay Gör"
        detailButton.setTextColor(Color.WHITE)
        detailButton.setBackgroundResource(R.drawable.bg_button_green)

        detailButton.setOnClickListener {
            val intent = Intent(this, DiseaseRecordDetailActivity::class.java)
            intent.putExtra("diseaseName", record.diseaseName)
            intent.putExtra("city", record.city)
            intent.putExtra("district", record.district)
            intent.putExtra("confidence", record.confidence)
            intent.putExtra("date", record.date)
            startActivity(intent)
        }

        card.addView(diseaseText)
        card.addView(locationText)
        card.addView(confidenceText)
        card.addView(dateText)
        card.addView(detailButton)

        diseaseMapContainer.addView(card)
    }
}