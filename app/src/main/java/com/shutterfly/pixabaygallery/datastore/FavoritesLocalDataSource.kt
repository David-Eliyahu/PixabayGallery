package com.shutterfly.pixabaygallery.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class FavoritesLocalDataSource(
    val dataStore: DataStore<Preferences>
) {
    private val GALLERY_ITEM_IDS_KEY = stringSetPreferencesKey("gallery_item_ids_key")

    fun observeFavoritesIds(): Flow<Set<String>> =
        dataStore.data.map { preferences ->
            preferences[GALLERY_ITEM_IDS_KEY].orEmpty()
        }

    suspend fun toggleItem(id: String) {
        val ids = getNewIdsList(id)
        setNewIdsList(ids)
    }

    private suspend fun getNewIdsList(id: String) =
        observeFavoritesIds().firstOrNull().orEmpty().toMutableSet().apply {
            if (contains(id)) {
                remove(id)
            } else {
                add(id)
            }
        }.toSet()

    private suspend fun setNewIdsList(ids: Set<String>) {
        dataStore.edit { preferences ->
            preferences[GALLERY_ITEM_IDS_KEY] = ids
        }
    }
}