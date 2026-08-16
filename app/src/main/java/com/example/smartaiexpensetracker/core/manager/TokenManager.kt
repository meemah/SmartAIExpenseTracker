package com.example.smartaiexpensetracker.core.manager

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import java.sql.Ref
import javax.inject.Inject
import androidx.core.content.edit


class TokenManager @Inject constructor (
    @ApplicationContext private val context: Context
) {
    companion object {
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
    }
    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_auth_prefs",
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun getAccessToken() : String? = prefs.getString( ACCESS_TOKEN,null)

    fun getRefreshToken() : String? = prefs.getString(REFRESH_TOKEN,null)

    fun clearTokens(){
        prefs.edit { clear() }
    }

    fun saveTokens(accessToken:String, refreshToken: String){
        prefs.edit {
            putString(ACCESS_TOKEN, accessToken).putString(REFRESH_TOKEN, refreshToken)
        }
    }
}