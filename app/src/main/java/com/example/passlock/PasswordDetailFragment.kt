package com.example.passlock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.passlock.PasswordApplication
import com.example.passlock.databinding.FragmentPasswordDetailBinding
import com.example.passlock.viewmodels.PasswordViewModel
import com.example.passlock.viewmodels.PasswordViewModelFactory
import com.example.passlock.model.Password
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PasswordDetailFragment: Fragment() {

    private val viewModel: PasswordViewModel by activityViewModels {
        PasswordViewModelFactory(
            (activity?.application as PasswordApplication).database
                .PasswordDao()
        )
    }
    //grabbing password entity
    lateinit var password: Password
    //safe args
    private val navigationArgs: PasswordDetailFragmentArgs by navArgs()
    //data binding
    private var _binding: FragmentPasswordDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPasswordDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun bind(password: Password) {
        binding.apply {
            name.text = password.passwordName
            passwordDetail.text = password.passwordActual
            notes.text = password.notes
            deleteDetailButton.setOnClickListener {showConfirmationDialog()}
            editDetailFloatingActionButton.setOnClickListener {editPassword()}
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.passwordId
        viewModel.retrievePassword(id).observe(this.viewLifecycleOwner) { selectedPassword ->
            password = selectedPassword
            bind(password)
        }
    }

    /**
     * Displays an alert dialog to get the user's confirmation before deleting the item.
     */
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deletePassword()
            }
            .show()
    }

    private fun deletePassword() {
        viewModel.deletePassword(password)
        findNavController().navigateUp()
    }

    private fun editPassword() {
        val action = PasswordDetailFragmentDirections.actionPasswordDetailFragmentToAddPasswordFragment(
            password.passwordId
        )
        this.findNavController().navigate(action)
    }

    /**
     * Called when fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}