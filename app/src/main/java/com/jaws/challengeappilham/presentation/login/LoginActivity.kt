package com.jaws.challengeappilham.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.jaws.challengeappilham.R
import com.jaws.challengeappilham.databinding.ActivityLoginBinding
import com.jaws.challengeappilham.presentation.main.MainActivity
import com.jaws.challengeappilham.presentation.register.RegisterActivity
import com.jaws.challengeappilham.utils.highLightWord
import com.jaws.challengeappilham.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupForm()
        setClickListeners()
        observeResult()
    }

    private fun setupForm() {
        binding.layoutForm.tilEmail.isVisible = true
        binding.layoutForm.tilPassword.isVisible = true
    }

    private fun observeResult() {
        viewModel.loginResult.observe(this){
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogin.isVisible = true
                    binding.btnLogin.isEnabled = false
                    navigateToMain()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnLogin.isVisible = false
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogin.isVisible = true
                    binding.btnLogin.isEnabled = true
                    Toast.makeText(
                        this,
                        getString(
                            R.string.login_failed,
                            it.exception?.message.orEmpty()
                        ),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun setClickListeners() {
        binding.tvNavToRegister.highLightWord(getString(R.string.text_register)){
            navigateToRegister()
        }

        binding.btnLogin.setOnClickListener {
            doLogin()
        }
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
    }

    private fun doLogin() {
        if(isFormValid()){
            val email = binding.layoutForm.etEmail.text.toString().trim()
            val password = binding.layoutForm.etPassword.text.toString().trim()
            viewModel.doLogin(email, password)
        }
    }

    private fun isFormValid(): Boolean {
        val email = binding.layoutForm.etEmail.text.toString().trim()
        val password = binding.layoutForm.etPassword.text.toString().trim()
        return checkEmailValidation(email) && checkPasswordValidation(password)
    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (email.isEmpty()){
            //email cannot be empty
            binding.layoutForm.tilEmail.isErrorEnabled = true
            binding.layoutForm.tilEmail.error = getString(R.string.text_error_email_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //email format is correct
            binding.layoutForm.tilEmail.isErrorEnabled = true
            binding.layoutForm.tilEmail.error = getString(R.string.text_error_email_invalid)
            false
        } else {
            binding.layoutForm.tilEmail.isErrorEnabled = false
            true
        }
    }

    private fun checkPasswordValidation(
        password: String,
    ): Boolean {
        return if (password.isEmpty()){
            binding.layoutForm.tilPassword.isErrorEnabled = true
            binding.layoutForm.tilPassword.error = getString(R.string.text_error_password_empty)
            false
        } else if (password.length < 8){
            binding.layoutForm.tilPassword.isErrorEnabled = true
            binding.layoutForm.tilPassword.error = getString(R.string.text_error_password_less_than_8_char)
            false
        } else{
            binding.layoutForm.tilPassword.isErrorEnabled = false
            true
        }
    }
}