package com.example.giphy_update.ui.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import com.example.giphy_update.R
import com.example.giphy_update.databinding.FragmentWebviewBinding
import com.example.giphy_update.ui.base.BaseFragment

class WebViewFragment : BaseFragment<FragmentWebviewBinding>(R.layout.fragment_webview) {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            lifecycleOwner = this@WebViewFragment

            val url = requireActivity().intent.getStringExtra("movie")
                ?: run {
                    //finish()
                    return
                }
            webview.apply {
                webViewClient = WebViewClient()
                settings.run {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                }
            }

            webview.loadUrl(url)
        }
    }
}