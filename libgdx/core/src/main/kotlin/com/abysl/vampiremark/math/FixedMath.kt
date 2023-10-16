package com.abysl.vampiremark.math

import com.abysl.vampiremark.math.FixedMathTables.ACOS_TABLE
import com.abysl.vampiremark.math.FixedMathTables.ATAN_TABLE
import com.abysl.vampiremark.math.FixedMathTables.SIN_TABLE
import com.abysl.vampiremark.math.FixedPoint.Companion.FIXED_POINT_FACTOR
import kotlin.math.absoluteValue

object FixedMath {
    // Trigonometric Functions
    fun sin(angleDegrees: Int): FixedPoint {
        val normalizedAngle = angleDegrees % 360
        return when {
            normalizedAngle < 0 -> -sin(-normalizedAngle)
            normalizedAngle <= 90 -> FixedPoint(SIN_TABLE[normalizedAngle])
            normalizedAngle <= 180 -> FixedPoint(SIN_TABLE[180 - normalizedAngle])
            normalizedAngle <= 270 -> -FixedPoint(SIN_TABLE[normalizedAngle - 180])
            else -> -FixedPoint(SIN_TABLE[360 - normalizedAngle])
        }
    }

    fun cos(angleDegrees: Int): FixedPoint {
        return sin(angleDegrees + 90)
    }

    fun tan(angleDegrees: Int): FixedPoint {
        val cosineValue = cos(angleDegrees)
        if (cosineValue.rawValue.absoluteValue < 32768) {
            return FixedPoint(Int.MAX_VALUE)
        }
        return sin(angleDegrees) / cosineValue
    }

    fun asin(sineValue: FixedPoint): FixedPoint {
        // Derive asin from acos: asin(x) = π/2 - acos(x)
        return FixedPoint.fromInt(90 * FIXED_POINT_FACTOR) - acos(sineValue)
    }

    fun acos(cosineValue: FixedPoint): FixedPoint {
        val clampedCosineValue = cosineValue.rawValue.coerceIn(-FIXED_POINT_FACTOR, FIXED_POINT_FACTOR)
        val index = ((clampedCosineValue + FIXED_POINT_FACTOR) * (ACOS_TABLE.size - 1) / (2 * FIXED_POINT_FACTOR)).toInt()
        return FixedPoint(ACOS_TABLE[index])
    }

    fun atan2(y: FixedPoint, x: FixedPoint): FixedPoint {
        if (x.rawValue > 0) {
            return atan(y / x)
        } else if (x.rawValue < 0) {
            return if (y.rawValue >= 0) atan(y / x) + FixedPoint.fromInt(180) else atan(y / x) - FixedPoint.fromInt(180)
        } else if (y.rawValue > 0) {
            return FixedPoint.fromInt(90)
        } else if (y.rawValue < 0) {
            return FixedPoint.fromInt(-90)
        }
        return FixedPoint(0)
    }

    // Auxiliary Functions
    fun sqrt(value: FixedPoint): FixedPoint {
        if (value.rawValue <= 0) return FixedPoint(0)
        var x = value
        var prevX: FixedPoint
        do {
            prevX = x
            x = (x + (value / x)) / FixedPoint.fromInt(2)
        } while ((x - prevX).rawValue.absoluteValue > 1)
        return x
    }

    private fun atan(z: FixedPoint): FixedPoint {
        if (z.rawValue == 0) return FixedPoint(0)
        if (z.rawValue.absoluteValue <= FIXED_POINT_FACTOR) {
            val index = (z.rawValue * ATAN_TABLE.size / FIXED_POINT_FACTOR).toInt()
            val alpha = z.rawValue - index * FIXED_POINT_FACTOR / ATAN_TABLE.size
            val interpolatedValue = ATAN_TABLE[index] + (alpha * (ATAN_TABLE[index + 1] - ATAN_TABLE[index]) / (FIXED_POINT_FACTOR / ATAN_TABLE.size))
            return FixedPoint(interpolatedValue.toInt())
        } else if (z.rawValue > FIXED_POINT_FACTOR) {
            val reciprocal = FixedPoint(FIXED_POINT_FACTOR) / z
            return FixedPoint.fromInt(90 * FIXED_POINT_FACTOR) - atan(reciprocal)
        } else {
            val reciprocal = FixedPoint(FIXED_POINT_FACTOR) / -z
            return -(FixedPoint.fromInt(90 * FIXED_POINT_FACTOR) - atan(reciprocal))
        }
    }
}
