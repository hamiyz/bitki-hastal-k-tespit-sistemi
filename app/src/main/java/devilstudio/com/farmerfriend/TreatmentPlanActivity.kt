package devilstudio.com.farmerfriend

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class TreatmentPlanActivity : AppCompatActivity() {

    private lateinit var treatmentTitleText: TextView
    private lateinit var treatmentRiskText: TextView
    private lateinit var treatmentSummaryText: TextView
    private lateinit var treatmentStepsContainer: LinearLayout
    private lateinit var treatmentProductsButton: Button

    private var diseaseName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_treatment_plan)

        treatmentTitleText = findViewById(R.id.treatmentTitleText)
        treatmentRiskText = findViewById(R.id.treatmentRiskText)
        treatmentSummaryText = findViewById(R.id.treatmentSummaryText)
        treatmentStepsContainer = findViewById(R.id.treatmentStepsContainer)
        treatmentProductsButton = findViewById(R.id.treatmentProductsButton)

        diseaseName = intent.getStringExtra("diseaseName") ?: "Hastalık seçilmedi"

        val plan = TreatmentPlanRepository.getTreatmentPlan(diseaseName)

        treatmentTitleText.text = "${plan.diseaseName}\nTedavi / Bakım Planı"
        treatmentRiskText.text = "Risk Seviyesi: ${plan.riskLevel}"
        treatmentSummaryText.text = plan.summary

        addSteps(plan.steps)

        treatmentProductsButton.setOnClickListener {
            val intent = Intent(this, ProductActivity::class.java)
            intent.putExtra("diseaseName", diseaseName)
            startActivity(intent)
        }
    }

    private fun addSteps(steps: ArrayList<String>) {
        treatmentStepsContainer.removeAllViews()

        for (i in 0 until steps.size) {
            val stepCard = LinearLayout(this)
            stepCard.orientation = LinearLayout.VERTICAL
            stepCard.setPadding(22, 18, 22, 18)
            stepCard.setBackgroundResource(R.drawable.bg_card_white)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 0, 14)
            stepCard.layoutParams = params

            val stepText = TextView(this)
            stepText.text = "${i + 1}. ${steps[i]}"
            stepText.textSize = 15f
            stepText.setTextColor(Color.DKGRAY)

            stepCard.addView(stepText)
            treatmentStepsContainer.addView(stepCard)
        }
    }
}