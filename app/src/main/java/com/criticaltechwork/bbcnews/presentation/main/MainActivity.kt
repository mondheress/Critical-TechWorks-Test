package com.criticaltechwork.bbcnews.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.criticaltechwork.bbcnews.R
import com.criticaltechwork.bbcnews.databinding.ActivityMainBinding
import com.criticaltechwork.bbcnews.presentation.base.BaseActivity
import com.criticaltechwork.bbcnews.presentation.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun inflateBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    private val viewModel: MainViewModel by viewModels()
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use title defined in build.gradle flavors
        title = getString(R.string.news_provider_title)

        setupRecyclerView()
        observeViewModel()

        // fingerprint identification
        checkBiometrics()
    }

    private fun checkBiometrics() {
        val biometricManager = BiometricManager.from(this)
        if (biometricManager.canAuthenticate(BIOMETRIC_STRONG) == BIOMETRIC_SUCCESS) {
            showBiometricPrompt()
        } else {
            viewModel.fetchNews()
        }
    }

    private fun showBiometricPrompt() {
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    viewModel.fetchNews()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(this@MainActivity, errString, Toast.LENGTH_SHORT).show()
                    finish()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.biometric_login_for_ctw_app))
            .setSubtitle(getString(R.string.log_in_using_your_biometric_credential))
            .setNegativeButtonText(getString(R.string.cancel))
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun setupRecyclerView() {
        mainAdapter = MainAdapter { article ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("EXTRA_ARTICLE", article)
            }
            startActivity(intent)
        }
        binding.sourceRecyclerView.adapter = mainAdapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.articles.collect { articles ->
                    mainAdapter.submitList(articles)
                    binding.progressBar.visibility = if (articles.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }
    }
}