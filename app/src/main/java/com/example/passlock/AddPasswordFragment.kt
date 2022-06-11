package com.example.passlock

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.passlock.MainActivity
import com.example.passlock.PasswordApplication
import com.example.passlock.databinding.FragmentPasswordAddBinding
import com.example.passlock.model.Password
import com.example.passlock.viewmodels.PasswordViewModel
import com.example.passlock.viewmodels.PasswordViewModelFactory

class AddPasswordFragment: Fragment() {

    //grabbing the view model & database
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
    private var _binding: FragmentPasswordAddBinding? = null
    private val binding get() = _binding!!
    //inflating layout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPasswordAddBinding.inflate(inflater, container, false)
        return binding.root
    }
    //function to make sure fields are filled (used within other functions to before adding or updating passwordItem
    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.nameInput.text.toString(),
            binding.passwordInput.text.toString(),
            binding.notesInput.text.toString()
        )
    }
    // coroutines to bind data
    private fun bind(password: Password) {
        binding.apply {
            nameInput.setText(password.passwordName, TextView.BufferType.SPANNABLE)
            passwordInput.setText(password.passwordActual, TextView.BufferType.SPANNABLE)
            notesInput.setText(password.notes, TextView.BufferType.SPANNABLE)
            saveButton.setOnClickListener { updatePassword() }
        }
    }

    private fun addNewPassword() {
        if (isEntryValid()) {
            viewModel.addNewPassword(
                binding.nameInput.text.toString(),
                binding.passwordInput.text.toString(),
                binding.notesInput.text.toString()
            )
        }
        val action = AddPasswordFragmentDirections.actionAddPasswordFragmentToPasswordDashboardFragment()
        findNavController().navigate(action)
    }

    private fun updatePassword() {
        if (isEntryValid()) {
            viewModel.updatePassword(
                this.navigationArgs.passwordId,
                this.binding.nameInput.text.toString(),
                this.binding.passwordInput.text.toString(),
                this.binding.notesInput.text.toString()
            )
        }
        val action = AddPasswordFragmentDirections.actionAddPasswordFragmentToPasswordDashboardFragment()
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.passwordId
        if (id > 0) {
            viewModel.retrievePassword(id).observe(this.viewLifecycleOwner) { selectedPassword ->
                password = selectedPassword
                bind(password)
            }
        } else {
            binding.saveButton.setOnClickListener{ addNewPassword() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }

}