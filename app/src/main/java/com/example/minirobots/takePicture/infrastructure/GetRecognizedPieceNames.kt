package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.takePicture.domain.entities.PieceName
import com.google.mlkit.vision.text.Text
import javax.inject.Inject

/*
    Returns a list of all the piece names recognized in the MLKitText
    The list is sorted from left to right. For now, y position is disregarded.
 */

class GetRecognizedPieceNames @Inject constructor(
    private val getLocalizedPieceName: GetLocalizedPieceName
) {
    operator fun invoke(mlKitText: Text): List<PieceName> {
        val localizedPieces = mutableListOf<LocalizedPieceName>()
        for (block in mlKitText.textBlocks) {
            for (line in block.lines) {
                val localizedPiece = getLocalizedPieceName(line) ?: continue
                localizedPieces.add(localizedPiece)
            }
        }
        return localizedPieces
            .sortedBy { it.x }
            .map { it.type }
    }
}