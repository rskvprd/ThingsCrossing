package com.app.thingscrossing.feature_account.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val AUTH_KEY = stringPreferencesKey(name = "auth_key")

val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")
