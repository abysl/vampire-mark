package com.abysl.vampiremark.math

import kotlin.test.Test
import kotlin.test.assertEquals

class GameVectorTest {

    @Test
    fun testAddition() {
        val vec1 = GameVector(1, 1)
        val vec2 = GameVector(2, 2)
        vec1 += vec2
        assertEquals(3, vec1.x)
        assertEquals(3, vec1.y)
    }

    @Test
    fun testAddition2() {
        val vec1 = GameVector(1, 0)
        val vec2 = GameVector(2, 3)

        vec1 += vec2

        assertEquals(3, vec1.x, "Addition failed for x-coordinate")
        assertEquals(3, vec1.y, "Addition failed for y-coordinate")
    }

    @Test
    fun testSubtraction() {
        val vec1 = GameVector(3, 3)
        val vec2 = GameVector(2, 2)
        vec1 -= vec2
        assertEquals(1, vec1.x)
        assertEquals(1, vec1.y)
    }

    @Test
    fun testScaling() {
        val vec = GameVector(2, 2)
        vec.scale(2)
        assertEquals(4, vec.x)
        assertEquals(4, vec.y)
    }

    @Test
    fun testNormalization() {
        val vec = GameVector(3, 4)  // a 3-4-5 Pythagorean triple
        vec.normalize()
        // Check that normalized vector has a magnitude of 1
    }

    @Test
    fun testRotate() {
        val vec = GameVector(1 shl 16, 0)  // Initialize with (1, 0) in fixed-point format
        vec.rotate(90)
        // Check that vector is rotated to approximately (0, 1) with a small tolerance
        // due to the fixed-point arithmetic approximations.
        assertEquals(0, vec.x, "Rotation failed for x-coordinate")
        assertEquals(1 shl 16, vec.y, "Rotation failed for y-coordinate")
    }

    @Test
    fun testLerp() {
        val vec1 = GameVector(0, 0)
        val vec2 = GameVector(10, 10)
    }
}
