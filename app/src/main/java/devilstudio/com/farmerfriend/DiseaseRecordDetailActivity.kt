package devilstudio.com.farmerfriend

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class DiseaseRecordDetailActivity : AppCompatActivity() {

    private lateinit var detailDiseaseText: TextView
    private lateinit var detailLocationText: TextView
    private lateinit var detailConfidenceText: TextView
    private lateinit var detailDateText: TextView
    private lateinit var detailRiskText: TextView
    private lateinit var detailSummaryText: TextView
    private lateinit var detailTreatmentButton: Button
    private lateinit var detailProductsButton: Button

    private var diseaseName: String = ""
    private var city: String = ""
    private var district: String = ""
    private var confidence: String = ""
    private var date: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disease_record_detail)

        detailDiseaseText = findViewById(R.id.detailDiseaseText)
        detailLocationText = findViewById(R.id.detailLocationText)
        detailConfidenceText = findViewById(R.id.detailConfidenceText)
        detailDateText = findViewById(R.id.detailDateText)
        detailRiskText = findViewById(R.id.detailRiskText)
        detailSummaryText = findViewById(R.id.detailSummaryText)
        detailTreatmentButton = findViewById(R.id.detailTreatmentButton)
        detailProductsButton = findViewById(R.id.detailProductsButton)

        diseaseName = intent.getStringExtra("diseaseName") ?: "Hastalık bilgisi yok"
        city = intent.getStringExtra("city") ?: "İl bilgisi yok"
        district = intent.getStringExtra("district") ?: "İlçe bilgisi yok"
        confidence = intent.getStringExtra("confidence") ?: "Güven bilgisi yok"
        date = intent.getStringExtra("date") ?: "Tarih bilgisi yok"

        val plan = TreatmentPlanRepository.getTreatmentPlan(diseaseName)

        detailDiseaseText.text = diseaseName
        detailLocationText.text = "Konum: $city / $district"
        detailConfidenceText.text = "Güven Oranı: $confidence"
        detailDateText.text = "Kayıt Tarihi: $date"
        detailRiskText.text = "Risk Seviyesi: ${plan.riskLevel}"
        detailSummaryText.text = plan.summary

        detailRiskText.setTextColor(getRiskColor(plan.riskLevel))

        detailTreatmentButton.setOnClickListener {
            val intent = Intent(this, TreatmentPlanActivity::class.java)
            intent.putExtra("diseaseName", diseaseName)
            startActivity(intent)
        }

        detailProductsButton.setOnClickListener {
            val intent = Intent(this, ProductActivity::class.java)
            intent.putExtra("diseaseName", diseaseName)
            startActivity(intent)
        }
    }

    private fun getRiskColor(riskLevel: String): Int {
        return when {
            riskLevel.contains("Yüksek", ignoreCase = true) -> Color.rgb(198, 40, 40)
            riskLevel.contains("Orta", ignoreCase = true) -> Color.rgb(255, 152, 0)
            riskLevel.contains("Düşük", ignoreCase = true) -> Color.rgb(19, 138, 54)
            else -> Color.DKGRAY
        }
    }
}