package com.lucas.petros.spotfylab.features.login.presentation.auth

import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.lucas.petros.spotfylab.BuildConfig.CLIENT_ID
import com.lucas.petros.spotfylab.features.login.data.Constants.REDIRECT_URI
import com.lucas.petros.spotfylab.features.login.data.Constants.RESPONSE_ERROR
import com.lucas.petros.spotfylab.features.login.data.Constants.RESPONSE_TYPE
import com.lucas.petros.spotfylab.features.login.data.Constants.SPOTIFY_AUTHORIZE_URL
import com.lucas.petros.spotfylab.features.login.data.Constants.USER_READ

class SpotifyAuthManager(
    private val webViewCallback: WebViewCallback,
    private val webView: WebView
) {

    fun configureWebView() {
        webViewCallback.onWebViewLoaded(true)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        val uri = Uri.parse(SPOTIFY_AUTHORIZE_URL)
            .buildUpon()
            .appendQueryParameter("response_type", RESPONSE_TYPE)
            .appendQueryParameter("client_id", CLIENT_ID)
            .appendQueryParameter("redirect_uri", REDIRECT_URI)
            .appendQueryParameter("scope", USER_READ)
            .build()

        webView.loadUrl(uri.toString())

        webView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                webViewCallback.onWebViewLoaded(false)
                webView.visibility = View.VISIBLE

            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url.toString()
                handleUrl(url)
                return false
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                val html = "<html><body style=\"background-color:black;\"></body></html>"
                webView.loadData(
                    html,
                    "text/html",
                    "utf-8"
                )
            }
        }

    }

    private fun handleUrl(url: String?) {
        if (url != null && url.startsWith(REDIRECT_URI)) {
            val uri = Uri.parse(url)
            val code = uri.getQueryParameter(RESPONSE_TYPE)
            val error = uri.getQueryParameter(RESPONSE_ERROR)

            if (!code.isNullOrBlank()) {
                webViewCallback.onAuthorizationCodeReceived(code)
            } else if (!error.isNullOrBlank()) {
                webViewCallback.onAuthorizationError(error)
            }

        }
    }
}

interface WebViewCallback {
    fun onAuthorizationCodeReceived(code: String)
    fun onAuthorizationError(error: String)
    fun onWebViewLoaded(isLoaded: Boolean)
}