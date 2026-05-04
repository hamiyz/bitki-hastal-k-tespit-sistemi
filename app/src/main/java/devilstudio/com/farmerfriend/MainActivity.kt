package devilstudio.com.farmerfriend

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.widget.Button
class MainActivity : AppCompatActivity() {

    private lateinit var mClassifier: Classifier
    private lateinit var mBitmap: Bitmap
    private lateinit var myDialog: Dialog

    private lateinit var loadingText: TextView
    private lateinit var warningText: TextView
    private lateinit var confidenceBar: ProgressBar

    private var pname: String? = ""
    private var pSymptoms: String? = ""
    private var pManage: String? = ""

    private var NameV: TextView? = null
    private var SymptomsV: TextView? = null
    private var ManageV: TextView? = null

    private val mCameraRequestCode = 0

    private val mGalleryRequestCode = 1
    private val mInputSize = 224
    private val mModelPath = "model.tflite"
    private val mLabelPath = "labels.txt"

    private var lastRawResult: String = ""
    private var lastCleanResult: String = ""
    private var lastConfidence: String = ""
    private var lastImagePath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)

        loadingText = findViewById(R.id.loadingText)
        warningText = findViewById(R.id.warningText)
        confidenceBar = findViewById(R.id.confidenceBar)

        loadingText.visibility = View.GONE
        warningText.visibility = View.GONE
        confidenceBar.progress = 0

        mClassifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)
        myDialog = Dialog(this)

        disease_info.setOnClickListener {
            customDialog()
        }

        mCameraButton.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, mCameraRequestCode)
        }

        mGalleryButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, mGalleryRequestCode)
        }

        historyButton.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }


        // Alt menü - Ana Sayfa
        navHome.setOnClickListener {
            Toast.makeText(this, "Zaten ana sayfadasınız", Toast.LENGTH_SHORT).show()
        }

        // Alt menü - Geçmiş
        navHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        // Alt menü - Hakkında
        navAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK || data == null) {
            return
        }

        try {
            val photo: Bitmap? = when (requestCode) {
                mCameraRequestCode -> {
                    data.extras?.get("data") as? Bitmap
                }

                mGalleryRequestCode -> {
                    val selectedImageUri = data.data
                    if (selectedImageUri != null) {
                        MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
                    } else {
                        null
                    }
                }

                else -> null
            }

            if (photo == null) {
                mResultTextView.text = "Fotoğraf alınamadı"
                return
            }

            analyzeBitmap(photo)

        } catch (e: Exception) {
            e.printStackTrace()

            loadingBar.visibility = View.GONE
            loadingText.visibility = View.GONE

            mResultTextView.text = "Analiz sırasında hata oluştu"
            Toast.makeText(this, "Analiz yapılamadı", Toast.LENGTH_SHORT).show()
        }
    }
    private fun analyzeBitmap(photo: Bitmap) {
        loadingBar.visibility = View.VISIBLE
        loadingText.visibility = View.VISIBLE
        warningText.visibility = View.GONE
        confidenceBar.progress = 0

        mResultTextView.text = "Analiz ediliyor..."
        mResultTextView_2.text = ""

        mBitmap = scaleImage(photo)
        mPhotoImageView.setImageBitmap(mBitmap)

        val modelOutput = mClassifier.recognizeImage(mBitmap).firstOrNull()

        lastRawResult = modelOutput?.title ?: "Bilinmiyor"

        val confidenceValue = (modelOutput?.confidence ?: 0f) * 100
        lastConfidence = "%.2f%%".format(confidenceValue)
        confidenceBar.progress = confidenceValue.toInt()

        if (lastRawResult == "No_Leaf") {
            lastCleanResult = "Yaprak algılanamadı"

            mResultTextView.text = lastCleanResult
            mResultTextView_2.text = "Lütfen sadece bitki yaprağı fotoğrafı çekin."
            mResultTextView.setTextColor(Color.rgb(198, 40, 40))

            warningText.visibility = View.VISIBLE
            warningText.text = "⚠️ Yaprak bulunamadı!"

            loadingBar.visibility = View.GONE
            loadingText.visibility = View.GONE
            return
        }

        if (confidenceValue < 50) {
            lastRawResult = "Bilinmiyor"
            lastCleanResult = "Sonuç güvenilir değil"

            mResultTextView.text = lastCleanResult
            mResultTextView_2.text = "Güven düşük: $lastConfidence"
            mResultTextView.setTextColor(Color.rgb(198, 40, 40))

            warningText.visibility = View.VISIBLE
            warningText.text = "⚠️ Fotoğraf net değil veya yaprak algılanamadı."

            loadingBar.visibility = View.GONE
            loadingText.visibility = View.GONE
            return
        }

        lastCleanResult = translateLabel(lastRawResult)

        mResultTextView.text = lastCleanResult
        mResultTextView_2.text = "Güven: $lastConfidence"

        warningText.visibility = View.GONE

        if (lastRawResult.contains("healthy", ignoreCase = true)) {
            mResultTextView.setTextColor(Color.rgb(46, 125, 50))
        } else {
            mResultTextView.setTextColor(Color.rgb(198, 40, 40))
        }

        lastImagePath = saveBitmapToInternalStorage(mBitmap)
        saveHistory(lastImagePath, lastCleanResult, lastConfidence)

        loadingBar.visibility = View.GONE
        loadingText.visibility = View.GONE

        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 600
        resultCard.startAnimation(fadeIn)
    }
    private fun saveBitmapToInternalStorage(bitmap: Bitmap): String {
        val filename = "prediction_${System.currentTimeMillis()}.jpg"
        val file = File(filesDir, filename)

        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        outputStream.flush()
        outputStream.close()

        return file.absolutePath
    }

    private fun saveHistory(imagePath: String, result: String, confidence: String) {
        try {
            val prefs = getSharedPreferences("prediction_history", MODE_PRIVATE)
            val oldData = prefs.getString("items", "[]") ?: "[]"

            val oldArray = JSONArray(oldData)
            val newArray = JSONArray()

            val item = JSONObject()
            item.put("imagePath", imagePath)
            item.put("result", result)
            item.put("confidence", confidence)
            item.put(
                "date",
                SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date())
            )

            newArray.put(item)

            for (i in 0 until oldArray.length()) {
                if (newArray.length() >= 30) break
                newArray.put(oldArray.getJSONObject(i))
            }

            prefs.edit().putString("items", newArray.toString()).apply()

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Geçmiş kaydedilemedi", Toast.LENGTH_SHORT).show()
        }
    }

    private fun customDialog() {
        if (lastRawResult.isEmpty()) {
            Toast.makeText(this, "Önce fotoğraf çekip analiz yapın", Toast.LENGTH_SHORT).show()
            return
        }

        myDialog.setContentView(R.layout.detail_dailog_act)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()

        NameV = myDialog.findViewById(R.id.pltd_name)
        SymptomsV = myDialog.findViewById(R.id.symptoms)
        ManageV = myDialog.findViewById(R.id.management)
        val closeDialogButton = myDialog.findViewById<Button>(R.id.closeDialogButton)

        closeDialogButton.setOnClickListener {
            myDialog.dismiss()
        }

        NameV!!.text = lastCleanResult

        if (lastRawResult == "No_Leaf") {
            SymptomsV!!.text = "Görüntüde bitki yaprağı tespit edilmedi."
            ManageV!!.text = "Lütfen yalnızca bitki yaprağını net, yakın ve iyi ışıkta fotoğraflayın."
            return
        }

        if (lastRawResult == "Bilinmiyor") {
            SymptomsV!!.text = "Sonuç güvenilir değil."
            ManageV!!.text = "Lütfen daha net ve yaprağın tamamını gösteren bir fotoğraf çekin."
            return
        }

        val sName = lastRawResult

        pSymptoms = "Belirti bilgisi bulunamadı."
        pManage = "Çözüm bilgisi bulunamadı."

        try {
            val obj = JSONObject(loadJSONFromAsset())
            val jArray = obj.getJSONArray("plant_disease")

            for (i in 0 until jArray.length()) {
                val plant = jArray.getJSONObject(i)
                pname = plant.getString("name")

                if (sName == pname) {
                    pSymptoms = plant.getString("symptoms")
                    pManage = plant.getString("management")
                    break
                }
            }

            SymptomsV!!.text = pSymptoms
            ManageV!!.text = pManage

        } catch (e: Exception) {
            e.printStackTrace()
            SymptomsV!!.text = "Veri okunamadı."
            ManageV!!.text = "data.json dosyasını kontrol edin."
        }
    }

    private fun loadJSONFromAsset(): String {
        val inputStream: InputStream = assets.open("data.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        val charset: Charset = Charsets.UTF_8

        inputStream.read(buffer)
        inputStream.close()

        return String(buffer, charset)
    }

    private fun scaleImage(bitmap: Bitmap?): Bitmap {
        if (bitmap == null) {
            return Bitmap.createBitmap(mInputSize, mInputSize, Bitmap.Config.ARGB_8888)
        }

        val width = bitmap.width
        val height = bitmap.height

        val scaledWidth = mInputSize.toFloat() / width
        val scaledHeight = mInputSize.toFloat() / height

        val matrix = Matrix()
        matrix.postScale(scaledWidth, scaledHeight)

        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
    }

    private fun translateLabel(label: String): String {
        return when (label) {

            "No_Leaf" -> "Yaprak Algılanamadı"

            "Apple___Apple_scab" -> "Elma - Kara Leke"
            "Apple___Black_rot" -> "Elma - Siyah Çürüklük"
            "Apple___Cedar_apple_rust" -> "Elma - Sedir Elma Pası"
            "Apple___healthy" -> "Elma - Sağlıklı"

            "Blueberry___healthy" -> "Yaban Mersini - Sağlıklı"

            "Cherry_(including_sour)___healthy" -> "Kiraz - Sağlıklı"
            "Cherry_(including_sour)___Powdery_mildew" -> "Kiraz - Külleme"

            "Corn_(maize)___Cercospora_leaf_spot Gray_leaf_spot" -> "Mısır - Gri Yaprak Lekesi"
            "Corn_(maize)___Common_rust_" -> "Mısır - Yaygın Pas"
            "Corn_(maize)___healthy" -> "Mısır - Sağlıklı"
            "Corn_(maize)___Northern_Leaf_Blight" -> "Mısır - Kuzey Yaprak Yanıklığı"

            "Grape___Black_rot" -> "Üzüm - Siyah Çürüklük"
            "Grape___Esca_(Black_Measles)" -> "Üzüm - Siyah Benek"
            "Grape___healthy" -> "Üzüm - Sağlıklı"
            "Grape___Leaf_blight_(Isariopsis_Leaf_Spot)" -> "Üzüm - Yaprak Yanıklığı"

            "Orange___Haunglongbing_(Citrus_greening)" -> "Portakal - Turunçgil Yeşillenme"

            "Peach___Bacterial_spot" -> "Şeftali - Bakteriyel Leke"
            "Peach___healthy" -> "Şeftali - Sağlıklı"

            "Pepper,_bell___Bacterial_spot" -> "Biber - Bakteriyel Leke"    
            "Pepper,_bell___healthy" -> "Biber - Sağlıklı"

            "Potato___Early_blight" -> "Patates - Erken Yanıklık"
            "Potato___healthy" -> "Patates - Sağlıklı"
            "Potato___Late_blight" -> "Patates - Geç Yanıklık"

            "Raspberry___healthy" -> "Ahududu - Sağlıklı"
            "Soybean___healthy" -> "Soya Fasulyesi - Sağlıklı"

            "Squash___Powdery_mildew" -> "Kabak - Külleme"

            "Strawberry___healthy" -> "Çilek - Sağlıklı"
            "Strawberry___Leaf_scorch" -> "Çilek - Yaprak Yanıklığı"

            "Tomato___Bacterial_spot" -> "Domates - Bakteriyel Leke"
            "Tomato___Early_blight" -> "Domates - Erken Yanıklık"
            "Tomato___healthy" -> "Domates - Sağlıklı"
            "Tomato___Late_blight" -> "Domates - Geç Yanıklık"
            "Tomato___Leaf_Mold" -> "Domates - Yaprak Küfü"
            "Tomato___Septoria_leaf_spot" -> "Domates - Septorya Yaprak Lekesi"
            "Tomato___Spider_mites Two-spotted_spider_mite" -> "Domates - İki Noktalı Kırmızı Örümcek"
            "Tomato___Target_Spot" -> "Domates - Hedef Leke"
            "Tomato___Tomato_mosaic_virus" -> "Domates - Mozaik Virüsü"
            "Tomato___Tomato_Yellow_Leaf_Curl_Virus" -> "Domates - Sarı Yaprak Kıvırcıklık Virüsü"

            else -> label.replace("___", " - ").replace("_", " ")
        }
    }
}