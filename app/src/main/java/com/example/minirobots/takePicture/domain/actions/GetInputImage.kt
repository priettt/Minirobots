package com.example.minirobots.takePicture.domain.actions

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class GetInputImage @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    operator fun invoke(uri: Uri): InputImage? = try {
        InputImage.fromFilePath(context, uri)
    } catch (e: IOException) {
        null
    }
}
