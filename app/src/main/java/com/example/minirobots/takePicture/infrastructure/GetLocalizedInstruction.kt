package com.example.minirobots.takePicture.infrastructure

import com.google.mlkit.vision.text.Text
import javax.inject.Inject

/*
    Gets the closest instruction from the MLKitLine's text,
    and wraps it in a LocalizedInstruction with the MLKitLine's corner point x and y position.
 */

class GetLocalizedInstruction @Inject constructor(
    private val getClosestInstruction: GetClosestInstruction
) {
    operator fun invoke(mlKitLine: Text.Line): LocalizedInstruction? {
        val closestInstruction = getClosestInstruction(mlKitLine.text) ?: return null
        return LocalizedInstruction(
            closestInstruction,
            mlKitLine.cornerPoints?.get(0)?.x ?: 0,
            mlKitLine.cornerPoints?.get(0)?.y ?: 0
        )
    }
}