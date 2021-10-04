package com.example.minirobots.takePicture.infrastructure

import com.google.mlkit.vision.text.Text
import javax.inject.Inject

class GetLocalizedInstruction @Inject constructor(
    private val getClosestInstruction: GetClosestInstruction
) {
    operator fun invoke(line: Text.Line): LocalizedInstruction? {
        val nearestInstruction = getClosestInstruction(line.text) ?: return null
        return LocalizedInstruction(
            nearestInstruction,
            line.cornerPoints?.get(0)?.x ?: 0,
            line.cornerPoints?.get(0)?.y ?: 0
        )
    }
}