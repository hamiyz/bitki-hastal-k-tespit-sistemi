package devilstudio.com.farmerfriend

import android.content.Context
import java.util.UUID

object DeviceIdManager {

    private const val PREF_NAME = "device_id_storage"
    private const val KEY_DEVICE_ID = "customer_device_id"

    fun getDeviceId(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        var deviceId = sharedPreferences.getString(KEY_DEVICE_ID, "")

        if (deviceId.isNullOrEmpty()) {
            deviceId = UUID.randomUUID().toString()

            sharedPreferences.edit()
                .putString(KEY_DEVICE_ID, deviceId)
                .apply()
        }

        return deviceId
    }
}