package com.example.minirobots.takePicture.domain.actions

import com.example.minirobots.takePicture.domain.entities.PieceName
import com.example.minirobots.takePicture.infrastructure.MinirobotsLogger
import com.example.minirobots.takePicture.infrastructure.StringDistanceCalculator
import javax.inject.Inject

const val DISTANCE_THRESHOLD = 2

/*
    Receives a text, compares it with all the available piece names
    and returns the closest piece name, if its distance is below the threshold.
 */

class GetMostSimilarPieceName @Inject constructor(
    private val stringDistanceCalculator: StringDistanceCalculator,
    private val logger: MinirobotsLogger
) {

    operator fun invoke(text: String): PieceName? {
        if (text.length <= 1) return null
        var mostSimilarPiece = MostSimilarPiece(null, Int.MAX_VALUE)

        for (piece in PieceName.values()) {
            val distanceWithText = stringDistanceCalculator.getDistance(text, piece.text)
            if (distanceWithText < mostSimilarPiece.distance) {
                mostSimilarPiece = MostSimilarPiece(piece, distanceWithText)
            }
        }

        logMostSimilarPiece(text, mostSimilarPiece)

        if (mostSimilarPiece.distance <= DISTANCE_THRESHOLD)
            return mostSimilarPiece.name
        return null
    }

    private fun logMostSimilarPiece(text: String, mostSimilarPiece: MostSimilarPiece) {
        logger.log(
            "MinirobotsDebug",
            "Recognizing text: $text. Most similar piece ${mostSimilarPiece.name?.text} with a distance ${mostSimilarPiece.distance}"
        )
    }

    private data class MostSimilarPiece(val name: PieceName?, val distance: Int)
}