package eric.bitria.hexon

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Application.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class Hexon : Application()