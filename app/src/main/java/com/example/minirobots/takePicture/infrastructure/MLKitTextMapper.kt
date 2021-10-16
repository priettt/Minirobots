package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.takePicture.domain.entities.PieceName
import com.google.mlkit.vision.text.Text
import javax.inject.Inject

/*
    Returns a list of all the piece names recognized in the MLKitText
    The list is sorted from left to right. For now, y position is disregarded.
 */

class MLKitTextMapper @Inject constructor(
    private val getLocalizedPieceName: GetLocalizedPieceName
) {
    fun map(mlKitText: Text): List<PieceName> =
        mlKitText.textBlocks
            .flatMap { it.lines }
            .mapNotNull { getLocalizedPieceName(it) }
            .sortedBy { it.x }
            .map { it.type }
}
