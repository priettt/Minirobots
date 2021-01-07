package com.example.minirobots.instructions.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.minirobots.R
import com.example.minirobots.databinding.FragmentInstructionsScreenBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InstructionsScreenFragment : Fragment(R.layout.fragment_instructions_screen) {

    private val viewModel: InstructionsScreenViewModel by viewModels()
    private lateinit var instructionsTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding(view)
        observeViewModel()
    }

    private fun setupBinding(view: View) {
        val binding = FragmentInstructionsScreenBinding.bind(view)
        instructionsTextView = binding.textViewInstructionsList
    }

    private fun observeViewModel() {
        viewModel.instructions.asLiveData().observe(viewLifecycleOwner, Observer { instructions ->
            instructionsTextView.text = instructions
        })
    }

}
