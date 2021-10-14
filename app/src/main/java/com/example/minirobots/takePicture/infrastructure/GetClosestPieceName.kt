package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.takePicture.domain.entities.PieceName
import javax.inject.Inject

const val DISTANCE_THRESHOLD = 3

/*
    Receives a text, compares it with all the available piece names
    and returns the closest piece name, if its distance is below the threshold.
 */

class GetClosestPieceName @Inject constructor(
    private val stringDistanceCalculator: StringDistanceCalculator,
    private val logger: MinirobotsLogger
) {

    operator fun invoke(text: String): PieceName? {
        var closestPiece = ClosestPiece(null, Int.MAX_VALUE)

        for (piece in PieceName.values()) {
            val distanceWithText = stringDistanceCalculator.getDistance(text, piece.text)
            if (distanceWithText < closestPiece.distance) {
                closestPiece = ClosestPiece(piece, distanceWithText)
            }
        }

        logClosestPiece(text, closestPiece)

        if (closestPiece.distance <= DISTANCE_THRESHOLD)
            return closestPiece.name
        return null
    }

    private fun logClosestPiece(text: String, closestPiece: ClosestPiece) {
        logger.log(
            this.javaClass.name,
            "Recognizing text: $text. Closest piece ${closestPiece.name?.text} with a distance ${closestPiece.distance}"
        )
    }

    private data class ClosestPiece(val name: PieceName?, val distance: Int)
}