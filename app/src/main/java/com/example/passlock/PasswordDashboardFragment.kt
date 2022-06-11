package com.example.passlock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.passlock.PasswordApplication
import com.example.passlock.adapter.PasswordListAdapter
import com.example.passlock.viewmodels.PasswordViewModel
import com.example.passlock.viewmodels.PasswordViewModelFactory
import com.example.passlock.databinding.FragmentPasswordDashboardBinding


class PasswordDashboardFragment: Fragment() {

    private val viewModel: PasswordViewModel by activityViewModels {
        PasswordViewModelFactory(
            (activity?.application as PasswordApplication).database.PasswordDao()
        )
    }

    private var _binding: FragmentPasswordDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PasswordListAdapter {
            val action = PasswordDashboardFragmentDirections.actionPasswordDashboardFragmentToPasswordDetailFragment(it.passwordId)
            this.findNavController().navigate(action)
        }
        binding.dashboardRecyclerView.adapter = adapter
        viewModel.allPasswords.observe(this.viewLifecycleOwner) { passwords ->
            passwords.let {
                adapter.submitList(it)
            }

        }
        binding.dashboardRecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.addPasswordFab.setOnClickListener {
            val action = PasswordDashboardFragmentDirections.actionPasswordDashboardFragmentToAddPasswordFragment()
            this.findNavController().navigate(action)
        }
    }
}