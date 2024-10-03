package com.example.fitnesstracker

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TipsActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tips)

        webView = findViewById(R.id.web_view)

        webView.webViewClient = WebViewClient()

        webView.settings.javaScriptEnabled = true


        webView.loadUrl("https://www.gov.br/saude/pt-br/assuntos/saude-brasil")

    }
}