package com.example.almufeedschool

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var offlineLayout: LinearLayout
    private lateinit var btnRetry: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            webView = findViewById(R.id.webView)
            offlineLayout = findViewById(R.id.offlineLayout)
            btnRetry = findViewById(R.id.btnRetry)

            setupWebView()
            loadWebsite()

            btnRetry.setOnClickListener {
                loadWebsite()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupWebView() {
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
        }

        webView.webChromeClient = WebChromeClient()

        // WebViewClient آمن يتعامل مع حالة فشل الاتصال بدقة بدون أن ينهار التطبيق
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                showOfflineScreen()
            }
        }
    }

    private fun loadWebsite() {
        offlineLayout.visibility = View.GONE
        webView.visibility = View.VISIBLE
        webView.loadUrl("https://mufeed-school.web.app")
    }

    private fun showOfflineScreen() {
        webView.visibility = View.GONE
        offlineLayout.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
