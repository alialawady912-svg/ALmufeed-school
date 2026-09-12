package com.example.almufeedschool

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)

        setupWebView()
        
        // تحميل الموقع مباشرة بدون تعقيد الفحص الذي يسبب المشكلة
        webView.loadUrl("https://mufeed-school.web.app")
    }

    private fun setupWebView() {
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            builtInZoomControls = true
            displayZoomControls = false
            // محاكاة متصفح الحاسوب لعرض الجداول كاملة وغير مقصوصة
            userAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
        }

        // دعم ظهور رسائل التنبيه والنوافذ المنبثقة الخاصة بالموقع
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = WebViewClient()
    }
}
