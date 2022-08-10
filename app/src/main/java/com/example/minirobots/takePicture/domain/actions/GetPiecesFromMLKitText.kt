package com.example.minirobots.takePicture.domain.actions

import com.example.minirobots.takePicture.domain.entities.PieceName
import com.google.mlkit.vision.text.Text
import javax.inject.Inject

/*
    Returns a list of all the piece names recognized in the MLKitText
    The list is sorted from left to right. For now, y position is disregarded.
 */

class GetPiecesFromMLKitText @Inject constructor(
    private val getLocalizedPieceName: GetLocalizedPieceName
) {
    operator fun invoke(mlKitText: Text): List<PieceName> =
        mlKitText.textBlocks
            .flatMap { it.lines }
            .mapNotNull { getLocalizedPieceName(it) }
            .sortedBy { it.x }
            .map { it.type }
}
