package com.SuperfumeKotlin.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Gestor de tokens JWT
 * Utiliza SharedPreferences encriptadas para almacenar el token de forma segura
 */
@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        PREFS_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        private const val PREFS_NAME = "superfume_secure_prefs"
        private const val KEY_TOKEN = "jwt_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_ROLE_ID = "user_role_id"
        private const val KEY_USER_ROLE_NAME = "user_role_name"
    }

    /**
     * Guarda el token JWT
     */
    fun saveToken(token: String) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply()
    }

    /**
     * Obtiene el token JWT guardado
     */
    fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    /**
     * Verifica si hay un token guardado
     */
    fun hasToken(): Boolean {
        return !getToken().isNullOrEmpty()
    }

    /**
     * Elimina el token JWT
     */
    fun clearToken() {
        sharedPreferences.edit().remove(KEY_TOKEN).apply()
    }

    /**
     * Guarda información del usuario incluyendo rol
     */
    fun saveUserInfo(userId: Int, email: String, roleId: Int, roleName: String) {
        sharedPreferences.edit()
            .putInt(KEY_USER_ID, userId)
            .putString(KEY_USER_EMAIL, email)
            .putInt(KEY_USER_ROLE_ID, roleId)
            .putString(KEY_USER_ROLE_NAME, roleName)
            .apply()
    }

    /**
     * Obtiene el ID del usuario guardado
     */
    fun getUserId(): Int {
        return sharedPreferences.getInt(KEY_USER_ID, -1)
    }

    /**
     * Obtiene el email del usuario guardado
     */
    fun getUserEmail(): String? {
        return sharedPreferences.getString(KEY_USER_EMAIL, null)
    }

    /**
     * Obtiene el ID del rol del usuario
     */
    fun getUserRoleId(): Int {
        return sharedPreferences.getInt(KEY_USER_ROLE_ID, -1)
    }

    /**
     * Obtiene el nombre del rol del usuario
     */
    fun getUserRoleName(): String? {
        return sharedPreferences.getString(KEY_USER_ROLE_NAME, null)
    }

    /**
     * Verifica si el usuario es administrador (rol ID = 1)
     */
    fun isAdmin(): Boolean {
        return getUserRoleId() == 1
    }

    /**
     * Verifica si el usuario es cliente (rol ID = 2)
     */
    fun isCliente(): Boolean {
        return getUserRoleId() == 2
    }

    /**
     * Limpia toda la información guardada
     */
    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }
}
