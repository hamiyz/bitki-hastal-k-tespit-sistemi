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

class DiseaseRecordActivity : AppCompatActivity() {

    private lateinit var diseaseRecordTitleText: TextView
    private lateinit var recordCityEditText: EditText
    private lateinit var recordDistrictEditText: EditText
    private lateinit var saveDiseaseRecordButton: Button

    private var diseaseName: String = ""
    private var confidence: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disease_record)

        diseaseRecordTitleText = findViewById(R.id.diseaseRecordTitleText)
        recordCityEditText = findViewById(R.id.recordCityEditText)
        recordDistrictEditText = findViewById(R.id.recordDistrictEditText)
        saveDiseaseRecordButton = findViewById(R.id.saveDiseaseRecordButton)

        diseaseName = intent.getStringExtra("diseaseName") ?: ""
        confidence = intent.getStringExtra("confidence") ?: ""

        diseaseRecordTitleText.text = "Hastalık Kaydı\n$diseaseName\nGüven: $confidence"

        saveDiseaseRecordButton.setOnClickListener {
            saveDiseaseRecord()
        }
    }

    private fun saveDiseaseRecord() {
        val city = recordCityEditText.text.toString().trim()
        val district = recordDistrictEditText.text.toString().trim()

        if (city.isEmpty()) {
            recordCityEditText.error = "İl giriniz"
            return
        }

        if (district.isEmpty()) {
            recordDistrictEditText.error = "İlçe giriniz"
            return
        }

        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        val record = DiseaseRecord(
            diseaseName = diseaseName,
            city = city,
            district = district,
            confidence = confidence,
            date = currentDate
        )

        DiseaseRecordStorage.saveRecord(this, record)

        Toast.makeText(
            this,
            "Bölgesel hastalık kaydı oluşturuldu.",
            Toast.LENGTH_LONG
        ).show()

        finish()
    }
}