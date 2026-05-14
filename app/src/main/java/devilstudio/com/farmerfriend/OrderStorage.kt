package devilstudio.com.farmerfriend

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object OrderStorage {

    private const val PREF_NAME = "order_storage"
    private const val KEY_ORDERS = "orders"

    fun saveOrder(context: Context, order: OrderRequest) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val oldData = sharedPreferences.getString(KEY_ORDERS, "[]")
        val jsonArray = JSONArray(oldData)

        val jsonObject = JSONObject()
        jsonObject.put("fullName", order.fullName)
        jsonObject.put("phone", order.phone)
        jsonObject.put("city", order.city)
        jsonObject.put("district", order.district)
        jsonObject.put("productName", order.productName)
        jsonObject.put("diseaseName", order.diseaseName)
        jsonObject.put("productCategory", order.productCategory)
        jsonObject.put("productPrice", order.productPrice)
        jsonObject.put("note", order.note)
        jsonObject.put("status", order.status)
        jsonObject.put("date", order.date)
        jsonObject.put("sellerId", order.sellerId)
        jsonObject.put("sellerName", order.sellerName)
        jsonObject.put("customerDeviceId", order.customerDeviceId)

        jsonArray.put(jsonObject)

        sharedPreferences.edit()
            .putString(KEY_ORDERS, jsonArray.toString())
            .apply()
    }

    fun getOrders(context: Context): ArrayList<OrderRequest> {
        val orderList = ArrayList<OrderRequest>()

        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val data = sharedPreferences.getString(KEY_ORDERS, "[]")
        val jsonArray = JSONArray(data)

        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)

            val order = OrderRequest(
                fullName = item.optString("fullName"),
                phone = item.optString("phone"),
                city = item.optString("city"),
                district = item.optString("district"),
                productName = item.optString("productName"),
                diseaseName = item.optString("diseaseName"),
                productCategory = item.optString("productCategory"),
                productPrice = item.optString("productPrice"),
                note = item.optString("note"),
                status = item.optString("status", "Beklemede"),
                date = item.optString("date"),
                sellerId = item.optInt("sellerId", 0),
                sellerName = item.optString("sellerName", ""),
                customerDeviceId = item.optString("customerDeviceId", "")
            )

            orderList.add(order)
        }

        return orderList
    }

    fun updateOrderStatus(context: Context, orderIndex: Int, newStatus: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val data = sharedPreferences.getString(KEY_ORDERS, "[]")
        val jsonArray = JSONArray(data)

        if (orderIndex < 0 || orderIndex >= jsonArray.length()) {
            return
        }

        val orderObject = jsonArray.getJSONObject(orderIndex)
        orderObject.put("status", newStatus)

        sharedPreferences.edit()
            .putString(KEY_ORDERS, jsonArray.toString())
            .apply()
    }

    fun clearOrders(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .remove(KEY_ORDERS)
            .apply()
    }
}