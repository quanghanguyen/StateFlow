package com.example.stateflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.stateflow.databinding.ActivityMainBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    //Khoi tao viewBinding theo kieu chuan
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    @OptIn(ExperimentalCoroutinesApi::class)
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        login()
        // thay the observe bang collect
        collectFlow()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun login() {
        binding.signUp.setOnClickListener {
            viewModel.login(binding.userName.text.toString(), binding.password.text.toString())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun collectFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.loginState.collect { result->
                when(result) {
                    is MainViewModel.LoginUiState.Success -> {
                        Toast.makeText(this@MainActivity, result.successMessage, Toast.LENGTH_SHORT).show()
                        binding.progressBar.isVisible = false
                    }
                    is MainViewModel.LoginUiState.Error -> {
                        Toast.makeText(this@MainActivity, result.errorMessage, Toast.LENGTH_SHORT).show()
                        binding.progressBar.isVisible = false
                    }
                    is MainViewModel.LoginUiState.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}