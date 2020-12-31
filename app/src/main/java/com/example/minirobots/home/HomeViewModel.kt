package com.example.minirobots.home

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import dagger.hilt.android.qualifiers.ApplicationContext

class HomeViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context
) : ViewModel() {

    lateinit var pictureUri: Uri

    fun onTakePictureUriCreated(pictureUri: Uri) {
        this.pictureUri = pictureUri
    }

    fun onTakePictureCompleted() {
        val recognizer = TextRecognition.getClient()
        val inputImage = InputImage.fromFilePath(context, pictureUri)
        val result = recognizer.process(inputImage)
            .addOnSuccessListener { visionText ->
                Toast.makeText(context, visionText.text, Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->

            }
    }

}