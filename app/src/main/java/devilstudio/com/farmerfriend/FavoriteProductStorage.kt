package devilstudio.com.farmerfriend

import android.content.Context
import org.json.JSONArray

object FavoriteProductStorage {

    private const val PREF_NAME = "favorite_product_storage"
    private const val KEY_FAVORITES = "favorite_product_ids"

    fun addFavorite(context: Context, productId: Int) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val oldData = prefs.getString(KEY_FAVORITES, "[]")
        val jsonArray = JSONArray(oldData)

        for (i in 0 until jsonArray.length()) {
            if (jsonArray.getInt(i) == productId) {
                return
            }
        }

        jsonArray.put(productId)

        prefs.edit()
            .putString(KEY_FAVORITES, jsonArray.toString())
            .apply()
    }

    fun removeFavorite(context: Context, productId: Int) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val oldData = prefs.getString(KEY_FAVORITES, "[]")
        val oldArray = JSONArray(oldData)
        val newArray = JSONArray()

        for (i in 0 until oldArray.length()) {
            val id = oldArray.getInt(i)
            if (id != productId) {
                newArray.put(id)
            }
        }

        prefs.edit()
            .putString(KEY_FAVORITES, newArray.toString())
            .apply()
    }

    fun isFavorite(context: Context, productId: Int): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val data = prefs.getString(KEY_FAVORITES, "[]")
        val jsonArray = JSONArray(data)

        for (i in 0 until jsonArray.length()) {
            if (jsonArray.getInt(i) == productId) {
                return true
            }
        }

        return false
    }

    fun getFavoriteIds(context: Context): ArrayList<Int> {
        val favoriteIds = ArrayList<Int>()

        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val data = prefs.getString(KEY_FAVORITES, "[]")
        val jsonArray = JSONArray(data)

        for (i in 0 until jsonArray.length()) {
            favoriteIds.add(jsonArray.getInt(i))
        }

        return favoriteIds
    }
}