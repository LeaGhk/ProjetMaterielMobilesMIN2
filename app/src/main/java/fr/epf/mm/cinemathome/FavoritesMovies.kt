package fr.epf.mm.cinemathome

import android.content.Context
import android.content.SharedPreferences

object FavoritesManager {
    private const val PREF_NAME = "Favorites"
    private const val KEY_FAVORITES = "favorites"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getFavorites(context: Context): Set<String> {
        return getSharedPreferences(context).getStringSet(KEY_FAVORITES, setOf()) ?: setOf()
    }

    fun addFavorite(context: Context, movieId: Int) {
        val favorites = getFavorites(context).toMutableSet()
        favorites.add(movieId.toString())
        getSharedPreferences(context).edit().putStringSet(KEY_FAVORITES, favorites).apply()
    }

    fun removeFavorite(context: Context, movieId: Int) {
        val favorites = getFavorites(context).toMutableSet()
        favorites.remove(movieId.toString())
        getSharedPreferences(context).edit().putStringSet(KEY_FAVORITES, favorites).apply()
    }

    fun isFavorite(context: Context, movieId: Int): Boolean {
        return getFavorites(context).contains(movieId.toString())
    }


}
