package com.example.minirobots.takePicture.infrastructure

import android.graphics.Rect
import com.example.minirobots.takePicture.domain.entities.LocalizedPieceName
import com.example.minirobots.takePicture.domain.entities.PieceName
import com.example.minirobots.takePicture.domain.entities.PieceName.*
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.Text.*
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class MLKitTextMapperTest {

    private val getLocalizedPieceName = mockk<GetLocalizedPieceName>(relaxed = true)
    private val mlKitTextMapper = MLKitTextMapper(getLocalizedPieceName)

    private val funcionLine = Line("", Rect(), listOf(), "", listOf(Element("", Rect(), listOf(), "FUNCION")))
    private val avanzarLine = Line("", Rect(), listOf(), "", listOf(Element("", Rect(), listOf(), "AVANZAR")))
    private val retrocederLine = Line("", Rect(), listOf(), "", listOf(Element("", Rect(), listOf(), "RETROCEDER")))
    private val angleLine = Line("", Rect(), listOf(), "", listOf(Element("", Rect(), listOf(), "30°")))
    private val stepsLine = Line("", Rect(), listOf(), "", listOf(Element("", Rect(), listOf(), "2 DOS")))

    private val testText = Text(
        "", listOf(
            TextBlock("", Rect(), listOf(), "", listOf(funcionLine, avanzarLine)),
            TextBlock("", Rect(), listOf(), "", listOf(retrocederLine)),
            TextBlock("", Rect(), listOf(), "", listOf(angleLine, stepsLine)),
        )
    )

    private val emptyText = Text("", listOf())

    @Test
    fun `invoke with test text`() {
        givenGetLocalizedPieceReturnsCorrectPieces()
        val result = whenInvokingWithTestText()
        assertEquals(listOf(FUNCION, AVANZAR, RETROCEDER, ANGULO_30, NUMERO_2), result)
    }

    @Test
    fun `invoke without text`() {
        val result = whenInvokingWithoutText()
        assertEquals(emptyList<PieceName>(), result)
    }

    private fun givenGetLocalizedPieceReturnsCorrectPieces() {
        every { getLocalizedPieceName.invoke(funcionLine) } returns LocalizedPieceName(FUNCION, 0, 0)
        every { getLocalizedPieceName.invoke(avanzarLine) } returns LocalizedPieceName(AVANZAR, 1, 0)
        every { getLocalizedPieceName.invoke(retrocederLine) } returns LocalizedPieceName(RETROCEDER, 2, 0)
        every { getLocalizedPieceName.invoke(angleLine) } returns LocalizedPieceName(ANGULO_30, 3, 0)
        every { getLocalizedPieceName.invoke(stepsLine) } returns LocalizedPieceName(NUMERO_2, 4, 0)
    }

    private fun whenInvokingWithoutText() = mlKitTextMapper.map(emptyText)
    private fun whenInvokingWithTestText() = mlKitTextMapper.map(testText)

}
