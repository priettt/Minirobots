package com.example.minirobots.takePicture.infrastructure

import android.net.Uri
import com.example.minirobots.Instruction
import com.example.minirobots.takePicture.domain.actions.GetInputImage
import com.example.minirobots.takePicture.domain.actions.RecognizeImage
import javax.inject.Inject

/*
    Get input image, recognize them with MLKIT, map them to instructions, store them in repository.

    There are a few ways we could do this:

    - The way we do now: grab both actions and modifiers indistinctly, return them in a list ordered
     by their x position as the following: Instructions: [A1, M1, A2, M3, A3, A4, M4].
     We are not using the y position here, so in order to recognize the complete instructions,
     we have to search for modifiers of their type in their surroundings.
     If an action doesn't use modifiers, we jump to the next action and search for its modifiers.
     We suppose that the modifiers should be left or right of an action, and not further.
     If we can't find a modifier for an action, we put a random one. If there's a modifier without
     an action, it's probably not going to be picked up, unless the next action needs it and finds
     it on its left.
     Once a modifier is used to form an instruction, we should discard it so it's not used again.

     Happy case:
    Instruction names: [Derecha, 30 Grados, Avanzar, 2 Pasos, Retroceder, 3 Pasos, Retroceder, 4 Pasos, Izquierda, 36 Grados]

     Some modifiers inverted case:
    Instruction names: [30 Grados, Derecha, 2 Pasos, Avanzar, Retroceder, 3 Pasos, 4 Pasos, Retroceder, Izquierda, 36 Grados]

    Actions lost:
    Instruction names: [30 Grados, xx-Derecha-xx, 2 Pasos, Avanzar, xx-Retroceder-xx, 3 Pasos, 4 Pasos, Retroceder, Izquierda, 36 Grados]
    Instructions: [Avanzar 2, Retroceder 4, Izquierda 36]

    Modifiers lost:
    Instruction names: [xx-30 Grados-xx, Derecha, 2 Pasos, Avanzar, Retroceder, xx-3 Pasos-xx, 4 Pasos, Retroceder, Izquierda, 36 Grados]
    Instructions: [Derecha Random, Avanzar 2, Retroceder 4, Retroceder Random, Izquierda 36]

    Both lost:
    Instruction names: [30 Grados, xx-Derecha-xx, xx-2 Pasos-xx, Avanzar, Retroceder, xx-3 Pasos-xx, xx-4 Pasos-xx, Retroceder, Izquierda, 36 Grados]
    Instructions: [Avanzar Random, Retroceder Random, Retroceder 4, Retroceder Random, Izquierda 36]

    This alternative is a little more convoluted, as we have to search left and right for modifiers, but it is way more accurate
    than using different lists, mostly because of the offsets. We can probably find a way to avoid big offsets with the alternative 1 though.

    Takeaway: harder to implement, more accurate and offset resistant.

                            ------------------------------------------------------------------------------

    - Alternative 1: return a list of actions, and a list of modifiers, each one ordered by their own
    x positions. Example: Actions: [A1, A2, A3, A4, A5]. Modifiers: [M1, M3, M4].
    We still don't use the y position.
    For each action, if it needs a modifier, we grab the first compatible modifier in the list and discard it.
    Forming an instruction is way easier.
    If we have an action but it's modifier is lost, we could search the whole modifiers list to find a compatible
    one, but this would cause a huge offset once this happens multiple times.
    If we have a modifier without it's action, it could be grabbed by a compatible action, again causing an offset.
    This could be solved by only grabbing the first or second modifier if it's compatible, if not, we
    use a random modifier.

    Happy case:
    Actions: [Derecha, Avanzar, Retroceder, Retroceder, Izquierda]
    Modifiers: [30 Grados, 2 Pasos, 3 pasos, 2 pasos, 36 grados]

    First two lost:
    Actions: [Derecha, Avanzar, Retroceder, Retroceder, Izquierda]
    Modifiers: [xx-30 Grados-xx, xx-2 Pasos-xx, 3 pasos, 4 pasos, 36 grados]
    Instructions: [Derecha Random, Avanzar 3 pasos, Retroceder 4 pasos, Retroceder random, Izq 36]

    Middle two lost:
    Actions: [Derecha, Avanzar, Retroceder, Retroceder, Izquierda]
    Modifiers: [30 Grados, xx-2 Pasos-xx, xx-3 pasos-xx, 2 pasos, 36 grados]
    Instructions: [Derecha 30, Avanzar 2 pasos, Retroceder Random, Retroceder random, Izq 36]

    Actions lost:
    Actions: [xx-Derecha-xx, Avanzar, xx-Retroceder-xx, Retroceder, Izquierda]
    Modifiers: [30 Grados, 2 Pasos, 3 pasos, 2 pasos, 36 grados]
    Instructions: [Avanzar 2 pasos, Retroceder 3 pasos, Retroceder random, Izq 30]

    There's multiple offsets, and once it fails, it comes out different than the intended.

    Takeaway: easier to make instructions, less respectful of the original order.

                            ------------------------------------------------------------------------------

    Alternative 2: Use x position! We have two lists, actions and modifiers, each one with it's x position. For each action, we find
    a modifier from the other list, that falls between an x position threshold. This way, we don't really care about offsets because we are
    just going to get modifiers that are near the action. This sounds good, we should change some implementation details to make it work.

    Example:
    Actions: [Derecha(5), Avanzar(50), Retroceder(100), Retroceder(150), Izquierda(200)]
    Modifiers: [30 Grados(1), 2 Pasos(45), 3 pasos(109), 2 pasos(145), 36 grados(220)]

    If there isn't a modifier in the threshold, we use a random one. We ignore modifiers without actions.
    We should check which value do we really get for x positions, and how far apart instructions are, so we can define a sensible threshold.
    We should also check what happens when the picture is taken in an angle, kind of in diagonal.
 */

class MLKitInstructionsRecognizer @Inject constructor(
    private val getInputImage: GetInputImage,
    private val recognizeImage: RecognizeImage,
    private val getRecognizedInstructionNames: GetRecognizedInstructionNames,
    private val instructionNamesMapper: InstructionNamesMapper
) {

    suspend fun recognize(uri: Uri): List<Instruction> {
        val inputImage = getInputImage(uri) ?: return emptyList()
        val mlKitText = recognizeImage(inputImage) ?: return emptyList()
        val recognizedInstructionNames = getRecognizedInstructionNames(mlKitText)
        return instructionNamesMapper.map(recognizedInstructionNames)
    }

}
