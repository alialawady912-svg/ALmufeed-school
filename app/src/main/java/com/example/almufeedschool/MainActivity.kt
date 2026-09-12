package com.example.almufeedschool

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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

        webView = findViewById(R.id.webView)
        offlineLayout = findViewById(R.id.offlineLayout)
        btnRetry = findViewById(R.id.btnRetry)

        setupWebView()
        checkConnectionAndLoad()

        // زر إعادة المحاولة عند انقطاع الإنترنت
        btnRetry.setOnClickListener {
            checkConnectionAndLoad()
        }
    }

    private fun setupWebView() {
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            
            // ضبط العرض للشاشات لتناسب الجداول
            loadWithOverviewMode = true
            useWideViewPort = true
            
            // تفعيل التكبير والتصغير لرؤية الجداول والعروض بشكل كامل
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
        }

        // دعم الإشعارات، الرسائل، والتنبيهات الخاصة بالموقع
        webView.webChromeClient = WebChromeClient()

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

    private fun checkConnectionAndLoad() {
        if (isNetworkAvailable(this)) {
            offlineLayout.visibility = View.GONE
            webView.visibility = View.VISIBLE
            webView.loadUrl("https://mufeed-school.web.app")
        } else {
            showOfflineScreen()
        }
    }

    private fun showOfflineScreen() {
        webView.visibility = View.GONE
        offlineLayout.visibility = View.VISIBLE
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    // دعم زر الرجوع في الهاتف للتنقل داخل صفحات الموقع بسلاسة
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
