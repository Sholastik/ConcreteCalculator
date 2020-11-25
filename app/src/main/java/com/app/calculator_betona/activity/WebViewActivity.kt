package com.app.calculator_betona.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.calculator_betona.R
import com.app.calculator_betona.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityWebViewBinding

    companion object {
        const val EXTRA_URL = "Extra_URL"
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view)
        binding.webView.loadUrl(getUrl())
        binding.webView.settings.javaScriptEnabled = true
    }

    private fun getUrl() = intent.getStringExtra(EXTRA_URL)
}
