package com.example.minirobots.home

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.minirobots.R
import com.example.minirobots.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

private const val SAVED_PICTURE_PREFIX = "Minirobots-"
private const val FILE_PROVIDER_AUTHORITY = "com.minirobots.fileprovider"

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    private val getContent = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        onTakePictureCompleted(success)
    }

    private fun onTakePictureCompleted(success: Boolean) {
        if (success) {
            viewModel.onTakePictureCompleted()
        } else {
            Toast.makeText(requireContext(), "Couldn't retrieve picture, please, try again.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        binding.buttonTakePicture.setOnClickListener {
            onTakePictureClicked()
        }
    }

    private fun onTakePictureClicked() {
        val prefix = SAVED_PICTURE_PREFIX
        val postfix = System.currentTimeMillis().toString()
        val directory = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val pictureUri = FileProvider.getUriForFile(requireContext(), FILE_PROVIDER_AUTHORITY, File.createTempFile(prefix, postfix, directory));
        viewModel.onTakePictureUriCreated(pictureUri)
        getContent.launch(pictureUri)
    }
}