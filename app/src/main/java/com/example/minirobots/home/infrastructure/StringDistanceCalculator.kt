package com.example.minirobots.home.infrastructure

import javax.inject.Inject
import kotlin.math.max

/*
The Levenshtein distance is a number that tells you how different two strings are. The higher the number, the more different the two strings are.
For example, the Levenshtein distance between “kitten” and “sitting” is 3 since, at a minimum, 3 edits are required to change one into the other.
An “edit” is defined by either an insertion of a character, a deletion of a character, or a replacement of a character.
 */

class LevenshteinDistanceCalculator @Inject constructor() : StringDistanceCalculator {
    override fun getDistance(first: String, second: String): Int {
        if (first.isEmpty() || second.isEmpty()) return max(first.length, second.length)

        // create two integer arrays of distances and initialize the first one
        val cost = IntArray(second.length + 1) { it }  // previous
        val newCost = IntArray(second.length + 1)         // current

        for (i in first.indices) {
            newCost[0] = i + 1
            for (j in second.indices) {
                val match = if (first[i].equals(second[j], true)) 0 else 1
                val costInsert = newCost[j] + 1
                val costReplace = cost[j] + match
                val costDelete = cost[j + 1] + 1
                newCost[j + 1] = minOf(costInsert, costReplace, costDelete)
            }
            // copy newCost to cost for next iteration
            for (j in 0..second.length) cost[j] = newCost[j]
        }
        return newCost[second.length]
    }
}

interface StringDistanceCalculator {
    fun getDistance(first: String, second: String): Int
}