package com.example.minirobots.takePicture.domain.actions

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognizer
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RecognizeImage @Inject constructor(
    private val recognizer: TextRecognizer
) {
    suspend operator fun invoke(inputImage: InputImage): Text? = try {
        recognizer.process(inputImage).await()
    } catch (e: Exception) {
        null
    }
}
