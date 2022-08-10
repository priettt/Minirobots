package com.example.minirobots.takePicture.domain.actions

import com.example.minirobots.takePicture.domain.entities.LocalizedPieceName
import com.google.mlkit.vision.text.Text
import javax.inject.Inject

/*
    Gets the closest piece from the MLKitLine's text,
    and wraps it in a LocalizedPiece with the MLKitLine's corner point x and y position.
 */

class GetLocalizedPieceName @Inject constructor(
    private val getMostSimilarPieceName: GetMostSimilarPieceName
) {
    operator fun invoke(mlKitLine: Text.Line): LocalizedPieceName? {
        val mostSimilarPieceName = getMostSimilarPieceName(mlKitLine.text) ?: return null
        return LocalizedPieceName(
            mostSimilarPieceName,
            mlKitLine.cornerPoints?.get(0)?.x ?: 0,
            mlKitLine.cornerPoints?.get(0)?.y ?: 0
        )
    }
}
