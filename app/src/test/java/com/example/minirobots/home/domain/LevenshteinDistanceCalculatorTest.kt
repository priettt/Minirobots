package com.example.minirobots.home.domain

import com.example.minirobots.home.infrastructure.LevenshteinDistanceCalculator
import org.junit.Before
import org.junit.Test

internal class LevenshteinDistanceCalculatorTest {

    private lateinit var calculator: LevenshteinDistanceCalculator

    @Before
    fun setUp() {
        calculator = LevenshteinDistanceCalculator()
    }

    @Test
    fun givenEqualStrings_distanceShouldBeZero() {
        val first = "GIRAR IZQUIERDA"
        val second = "GIRAR IZQUIERDA"
        assert(calculator.getDistance(first, second) == 0)
    }

    @Test
    fun givenAnEmptyString_distanceShouldBeTheLengthOfTheOtherOne() {
        val emptyString = ""
        val testString = "GIRAR IZQUIERDA"
        assert(calculator.getDistance(emptyString, testString) == testString.length)
        assert(calculator.getDistance(testString, emptyString) == testString.length)
    }

    @Test
    fun givenEqualStringsWithDifferentCase_distanceShouldBeZero() {
        val first = "GirAR iZquIerdA"
        val second = "GIRAR IZQUIERDA"
        assert(calculator.getDistance(first, second) == 0)
    }

    @Test
    fun givenTwoDifferentStrings_distanceShouldBeCorrect() {
        val first = "NUMERO 2"
        val second = "NUMERO 1"
        assert(calculator.getDistance(first, second) == 1)
    }

    @Test
    fun givenTwoStringsDifferingInLength_distanceShouldBeCorrect() {
        val first = "FUNCION"
        val second = "FUNCION FIN"
        assert(calculator.getDistance(first, second) == 4)
    }

    @Test
    fun givenTwoDifferentStrings_distanceShouldBeCommutative() {
        val first = "FUNCION"
        val second = "FUNCION FIN"
        assert(calculator.getDistance(first, second) == 4)
        assert(calculator.getDistance(second, first) == 4)
    }
}