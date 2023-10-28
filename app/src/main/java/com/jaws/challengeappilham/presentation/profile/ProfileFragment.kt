package com.jaws.challengeappilham.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.jaws.challengeappilham.R
import com.jaws.challengeappilham.databinding.FragmentProfileBinding
import com.jaws.challengeappilham.presentation.login.LoginActivity
import com.jaws.challengeappilham.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )
        setupForm()
        showUserData()
        setClickListeners()
        observeData()
    }

    private fun observeData() {
        viewModel.updateProfileResult.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbChangeProfileLoading.isVisible = false
                    binding.btnChangeProfile.isVisible = true
                    binding.btnChangeProfile.isEnabled = true
                },
                doOnLoading = {
                    binding.pbChangeProfileLoading.isVisible = true
                    binding.btnChangeProfile.isVisible = false
                },
                doOnError = {
                    binding.pbChangeProfileLoading.isVisible = false
                    binding.btnChangeProfile.isVisible = true
                    binding.btnChangeProfile.isEnabled = true
                    Toast.makeText(
                        requireContext(),
                        getString(
                            R.string.change_profile_failed,
                            it.exception?.message.orEmpty()
                        ),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun setClickListeners() {
        binding.btnChangeProfile.setOnClickListener {
            changeProfileData()
        }
        binding.btnChangePassword.setOnClickListener {
            requestChangePassword()
        }
        binding.btnLogout.setOnClickListener {
            doLogout()
        }
    }

    private fun doLogout() {
        AlertDialog.Builder(requireContext())
            .setMessage(
                getString(R.string.do_you_want_to_logout) +
                    "${viewModel.getCurrentUser()?.fullName}"
            )
            .setPositiveButton(getString(R.string.Yes)) { _, _ ->
                viewModel.doLogout()
                navigateToLogin()
            }.setNegativeButton(getString(R.string.No)) { _, _ ->
            }.create().show()
    }

    private fun navigateToLogin() {
        requireActivity().run {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun requestChangePassword() {
        viewModel.createChangePasswordReq()
        AlertDialog.Builder(requireContext())
            .setMessage(
                getString(R.string.change_password_request) +
                    "${viewModel.getCurrentUser()?.email}"
            )
            .setPositiveButton("Ok") { _, _ ->
            }.create().show()
    }

    private fun changeProfileData() {
        val fullName = binding.layoutForm.etName.text.toString().trim()
        if (isFormValid()) {
            viewModel.updateProfile(fullName)
        }
    }

    private fun isFormValid(): Boolean {
        val currentName = viewModel.getCurrentUser()?.fullName
        val newName = binding.layoutForm.etName.text.toString().trim()
        return checkNameValidation(currentName, newName)
    }

    private fun checkNameValidation(
        currentName: String?,
        newName: String
    ): Boolean {
        return if (newName.isEmpty()) {
            binding.layoutForm.tilName.isErrorEnabled = true
            binding.layoutForm.tilName.error = getString(
                R.string.text_error_name_cannot_empty
            )
            false
        } else if (newName == currentName) {
            binding.layoutForm.tilName.isErrorEnabled = true
            binding.layoutForm.tilName.error = getString(R.string.text_error_new_name_must_be_different)
            false
        } else {
            binding.layoutForm.tilName.isErrorEnabled = false
            true
        }
    }

    private fun showUserData() {
        binding.layoutForm.etName.setText(viewModel.getCurrentUser()?.fullName)
        binding.layoutForm.etEmail.setText(viewModel.getCurrentUser()?.email)
    }

    private fun setupForm() {
        binding.layoutForm.tilName.isVisible = true
        binding.layoutForm.tilEmail.isVisible = true
        binding.layoutForm.etEmail.isEnabled = false
    }
}
