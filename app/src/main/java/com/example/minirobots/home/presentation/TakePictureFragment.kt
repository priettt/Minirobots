package com.example.minirobots.home.presentation

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.minirobots.R
import com.example.minirobots.databinding.FragmentTakePictureBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

private const val SAVED_PICTURE_PREFIX = "Minirobots-"
private const val FILE_PROVIDER_AUTHORITY = "com.minirobots.fileprovider"

@AndroidEntryPoint
class TakePictureFragment : Fragment(R.layout.fragment_take_picture) {

    private val viewModel: TakePictureViewModel by viewModels()
    private lateinit var binding: FragmentTakePictureBinding

    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { takePictureSuccessful ->
        viewModel.onTakePictureCompleted(takePictureSuccessful)
    }

    private val getPictureFromGallery = registerForActivityResult(ActivityResultContracts.GetContent()) { pictureUri ->
        viewModel.onGetPictureFromGallery(pictureUri)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding(view)
        observeViewModel()
    }

    private fun setupBinding(view: View) {
        binding = FragmentTakePictureBinding.bind(view)
        binding.takePictureButton.setOnClickListener {
            onTakePictureClicked()
        }
        binding.getPictureFromGalleryButton.setOnClickListener {
            onGetPictureFromGalleryClicked()
        }
    }

    private fun observeViewModel() {
        viewModel.navigation.asLiveData().observe(viewLifecycleOwner, { state ->
            when (state) {
                TakePictureNavigation.GoToInstructions -> goToInstructionsScreen()
                TakePictureNavigation.ShowSpinner -> showSpinner()
                TakePictureNavigation.ShowRecognitionError -> {
                    Toast.makeText(requireContext(), "There was an error analyzing the image. Please try again", Toast.LENGTH_SHORT).show()
                    hideSpinner()
                }
                TakePictureNavigation.ShowTakePictureError -> {
                    Toast.makeText(requireContext(), "Couldn't retrieve picture, please, try again.", Toast.LENGTH_SHORT).show()
                    hideSpinner()
                }
                else -> { //Do nothing
                }
            }
        })
    }

    private fun showSpinner() {
        binding.loadingSpinner.visibility = VISIBLE
    }

    private fun hideSpinner() {
        binding.loadingSpinner.visibility = GONE
    }

    private fun goToInstructionsScreen() {
        hideSpinner()
        findNavController().navigate(
            TakePictureFragmentDirections.actionTakePictureFragmentToInstructionListFragment()
        )
    }

    private fun onGetPictureFromGalleryClicked() {
        getPictureFromGallery.launch("image/*")
    }

    private fun onTakePictureClicked() {
        val pictureUri = generatePictureUri()
        viewModel.onPictureUriCreated(pictureUri)
        takePicture.launch(pictureUri)
    }

    private fun generatePictureUri(): Uri {
        val prefix = SAVED_PICTURE_PREFIX
        val postfix = System.currentTimeMillis().toString()
        val directory = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return FileProvider.getUriForFile(requireContext(), FILE_PROVIDER_AUTHORITY, File.createTempFile(prefix, postfix, directory))
    }
}