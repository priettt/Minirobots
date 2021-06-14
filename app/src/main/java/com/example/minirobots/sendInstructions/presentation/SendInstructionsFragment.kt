package com.example.minirobots.sendInstructions.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.minirobots.R
import com.example.minirobots.databinding.FragmentSendInstructionsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendInstructionsFragment : Fragment(R.layout.fragment_send_instructions) {

    private val viewModel: SendInstructionsViewModel by viewModels()
    private lateinit var binding: FragmentSendInstructionsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding(view)
        observeViewModel()
        viewModel.onViewCreated()
    }

    private fun setupBinding(view: View) {
        binding = FragmentSendInstructionsBinding.bind(view)

    }

    private fun observeViewModel() {

    }

}