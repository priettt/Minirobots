package com.example.minirobots.takePicture.infrastructure

import com.google.mlkit.vision.text.Text
import javax.inject.Inject

/*
    Gets the closest piece from the MLKitLine's text,
    and wraps it in a LocalizedPiece with the MLKitLine's corner point x and y position.
 */

class GetLocalizedPieceName @Inject constructor(
    private val getClosestPieceName: GetClosestPieceName
) {
    operator fun invoke(mlKitLine: Text.Line): LocalizedPieceName? {
        val closestPieceName = getClosestPieceName(mlKitLine.text) ?: return null
        return LocalizedPieceName(
            closestPieceName,
            mlKitLine.cornerPoints?.get(0)?.x ?: 0,
            mlKitLine.cornerPoints?.get(0)?.y ?: 0
        )
    }
}