package com.example.minirobots

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.minirobots.databinding.FragmentInstructionsSentBinding

class InstructionsSentFragment : Fragment(R.layout.fragment_instructions_sent) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding(view)
    }

    private fun setupBinding(view: View) {
        val binding = FragmentInstructionsSentBinding.bind(view)
        binding.sendInstructionsSuccessButton.setOnClickListener {
            findNavController().navigate(
                InstructionsSentFragmentDirections.actionInstructionsSentFragmentToTakePictureFragment()
            )
        }
    }
}