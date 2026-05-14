package devilstudio.com.farmerfriend

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object DiseaseRecordStorage {

    private const val PREF_NAME = "disease_record_storage"
    private const val KEY_RECORDS = "records"

    fun saveRecord(context: Context, record: DiseaseRecord) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val oldData = prefs.getString(KEY_RECORDS, "[]")
        val jsonArray = JSONArray(oldData)

        val jsonObject = JSONObject()
        jsonObject.put("diseaseName", record.diseaseName)
        jsonObject.put("city", record.city)
        jsonObject.put("district", record.district)
        jsonObject.put("confidence", record.confidence)
        jsonObject.put("date", record.date)

        jsonArray.put(jsonObject)

        prefs.edit()
            .putString(KEY_RECORDS, jsonArray.toString())
            .apply()
    }

    fun getRecords(context: Context): ArrayList<DiseaseRecord> {
        val recordList = ArrayList<DiseaseRecord>()

        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val data = prefs.getString(KEY_RECORDS, "[]")
        val jsonArray = JSONArray(data)

        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)

            val record = DiseaseRecord(
                diseaseName = item.optString("diseaseName"),
                city = item.optString("city"),
                district = item.optString("district"),
                confidence = item.optString("confidence"),
                date = item.optString("date")
            )

            recordList.add(record)
        }

        return recordList
    }

    fun clearRecords(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .remove(KEY_RECORDS)
            .apply()
    }
}