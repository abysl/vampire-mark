package com.abysl.vampiremark.math

import com.abysl.vampiremark.math.FixedPoint.Companion.FIXED_POINT_FACTOR
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.math.*

class FixedMathTest {

    private val TOLERANCE = 0.02  // Acceptable error percentage (2%)

    @Test
    fun testSinDeterminism() {
        val angle = 45
        val firstResult = FixedMath.sin(angle)
        val secondResult = FixedMath.sin(angle)
        assertEquals(firstResult, secondResult)
    }

    @Test
    fun testSinAccuracy() {
        for (angle in 0..360 step 5) {
            val fixedResult = FixedMath.sin(angle).toDouble() / FIXED_POINT_FACTOR
            val actualResult = sin(Math.toRadians(angle.toDouble()))
            assertEquals(actualResult, fixedResult, TOLERANCE * actualResult)
        }
    }

    @Test
    fun testCosDeterminism() {
        val angle = 45
        val firstResult = FixedMath.cos(angle)
        val secondResult = FixedMath.cos(angle)
        assertEquals(firstResult, secondResult)
    }

    @Test
    fun testCosAccuracy() {
        for (angle in 0..360 step 5) {
            val fixedResult = FixedMath.cos(angle).toDouble() / FIXED_POINT_FACTOR
            val actualResult = cos(Math.toRadians(angle.toDouble()))
            assertEquals(actualResult, fixedResult, TOLERANCE * actualResult)
        }
    }

    @Test
    fun testTanDeterminism() {
        val angle = 45
        val firstResult = FixedMath.tan(angle)
        val secondResult = FixedMath.tan(angle)
        assertEquals(firstResult, secondResult)
    }

    @Test
    fun testTanAccuracy() {
        for (angle in 0..85 step 5) {  // Excluding angles near 90° due to infinity tan value
            val fixedResult = FixedMath.tan(angle).toDouble() / FIXED_POINT_FACTOR
            val actualResult = tan(Math.toRadians(angle.toDouble()))
            assertEquals(actualResult, fixedResult, TOLERANCE * actualResult)
        }
    }

    @Test
    fun testAsinDeterminism() {
        val value = FixedPoint(16384)  // Represents 0.25 in fixed-point format
        val firstResult = FixedMath.asin(value)
        val secondResult = FixedMath.asin(value)
        assertEquals(firstResult, secondResult)
    }

    @Test
    fun testAsinAccuracy() {
        for (i in 0..FIXED_POINT_FACTOR step 4096) {  // Steps of 1/16 from 0 to 1
            val fixedValue = FixedPoint(i)
            val fixedResult = FixedMath.asin(fixedValue).toDouble() / FIXED_POINT_FACTOR
            val actualResult = asin(i.toDouble() / FIXED_POINT_FACTOR)
            assertEquals(actualResult, fixedResult, TOLERANCE * actualResult)
        }
    }

    @Test
    fun testAcosDeterminism() {
        val value = FixedPoint(16384)  // Represents 0.25 in fixed-point format
        val firstResult = FixedMath.acos(value)
        val secondResult = FixedMath.acos(value)
        assertEquals(firstResult, secondResult)
    }

    @Test
    fun testAcosAccuracy() {
        for (i in 0..FIXED_POINT_FACTOR step 4096) {  // Steps of 1/16 from 0 to 1
            val fixedValue = FixedPoint(i)
            val fixedResult = FixedMath.acos(fixedValue).toDouble() / FIXED_POINT_FACTOR
            val actualResult = acos(i.toDouble() / FIXED_POINT_FACTOR)
            assertEquals(actualResult, fixedResult, TOLERANCE * actualResult)
        }
    }

    @Test
    fun testAtan2Determinism() {
        val y = FixedPoint(16384)
        val x = FixedPoint(16384)
        val firstResult = FixedMath.atan2(y, x)
        val secondResult = FixedMath.atan2(y, x)
        assertEquals(firstResult, secondResult)
    }

    @Test
    fun testAtan2Accuracy() {
        for (i in 0..FIXED_POINT_FACTOR step 4096) {
            val fixedY = FixedPoint(i)
            val fixedX = FixedPoint(FIXED_POINT_FACTOR - i)
            val fixedResult = FixedMath.atan2(fixedY, fixedX).toDouble() / FIXED_POINT_FACTOR
            val actualResult = atan2(i.toDouble() / FIXED_POINT_FACTOR, (FIXED_POINT_FACTOR - i).toDouble() / FIXED_POINT_FACTOR)
            assertEquals(actualResult, fixedResult, TOLERANCE * actualResult)
        }
    }

    @Test
    fun testSqrtDeterminism() {
        val value = FixedPoint(16384)  // Represents 0.25 in fixed-point format
        val firstResult = FixedMath.sqrt(value)
        val secondResult = FixedMath.sqrt(value)
        assertEquals(firstResult, secondResult)
    }

    @Test
    fun testSqrtAccuracy() {
        for (i in 0..FIXED_POINT_FACTOR step 4096) {  // Steps of 1/16 from 0 to 1
            val fixedValue = FixedPoint(i)
            val fixedResult = FixedMath.sqrt(fixedValue).toDouble() / FIXED_POINT_FACTOR
            val actualResult = sqrt(i.toDouble() / FIXED_POINT_FACTOR)
            assertEquals(actualResult, fixedResult, TOLERANCE * actualResult)
        }
    }
}

