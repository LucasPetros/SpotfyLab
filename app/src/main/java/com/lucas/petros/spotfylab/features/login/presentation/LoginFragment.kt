package com.lucas.petros.spotfylab.features.login.presentation

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.lucas.petros.commons.base.BaseFragmentVDB
import com.lucas.petros.commons.data.Constants.ACCESS_TOKEN
import com.lucas.petros.commons.data.Constants.TOKEN_REFRESH
import com.lucas.petros.commons.utils.SecureTokenManager
import com.lucas.petros.spotfylab.HomeActivity
import com.lucas.petros.spotfylab.R
import com.lucas.petros.spotfylab.databinding.FragmentLoginBinding
import com.lucas.petros.spotfylab.features.login.presentation.auth.SpotifyAuthManager
import com.lucas.petros.spotfylab.features.login.presentation.auth.WebViewCallback
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragmentVDB<FragmentLoginBinding>(R.layout.fragment_login),
    WebViewCallback {
    private val vm: LoginViewModel by viewModels()

    @Inject
    lateinit var secureTokenManager: SecureTokenManager

    private lateinit var spotifyAuthManager: SpotifyAuthManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webViewLogin = binding.web

        spotifyAuthManager = SpotifyAuthManager(this, webViewLogin)


    }



    override fun setupViewModel() {
        binding.vm = vm
    }

    override fun setupObservers() {
        requestLoginObserver()
        btnLoginObserver()
    }

    private fun requestLoginObserver() {
        vm.stateToken.observe(viewLifecycleOwner) { data ->
            if (data != null) {
                saveTokens(data.accessToken, data.tokenRefresh)
                requireActivity().finish()
                navigateToHomeScreen()

            }
        }
    }


    override fun onAuthorizationCodeReceived(code: String) {
        vm.getAccessToken(code)
    }

    override fun onAuthorizationError(error: String) {
        Toast.makeText(requireContext(), getString(R.string.error_request_auth), Toast.LENGTH_SHORT)
            .show()
    }

    override fun onWebViewLoaded(isLoaded: Boolean) {
        vm.showLoading(isLoaded)
    }

    private fun btnLoginObserver() {
        vm.btnLoginState.observe(viewLifecycleOwner) { state ->
            state.getContentIfNotHandled()?.let {
                if (isNetworkAvailable(requireActivity())) {
                    spotifyAuthManager.configureWebView()
                } else {
                    Toast.makeText(requireActivity(),
                        getString(R.string.conect_login), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveTokens(accessToken: String, tokenRefresh: String) {
        secureTokenManager.saveToken(ACCESS_TOKEN, accessToken)
        secureTokenManager.saveToken(TOKEN_REFRESH, tokenRefresh)
    }

    private fun navigateToHomeScreen() {
        startActivity(Intent(requireActivity(), HomeActivity::class.java))
    }
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

        return capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }

}
