package com.abysl.vampiremark.math

object FixedPointMath {
    private const val FIXED_POINT_SHIFT = 16
    private const val FIXED_POINT_FACTOR = 1 shl FIXED_POINT_SHIFT
    private const val ANGLE_MAX = 360
    private const val ANGLE_TO_INDEX_SCALE = FIXED_POINT_FACTOR / ANGLE_MAX

    // Precomputed lookup tables for sine and cosine values
    private val sinTable = IntArray(ANGLE_MAX) { index ->
        (kotlin.math.sin(index.toDouble() * kotlin.math.PI / 180) * FIXED_POINT_FACTOR).toInt()
    }
    private val cosTable = IntArray(ANGLE_MAX) { index ->
        (kotlin.math.cos(index.toDouble() * kotlin.math.PI / 180) * FIXED_POINT_FACTOR).toInt()
    }

    fun sin(angleFixed: Int): Int {
        val index = (angleFixed / ANGLE_TO_INDEX_SCALE) % ANGLE_MAX
        return sinTable[index]
    }

    fun cos(angleFixed: Int): Int {
        val index = (angleFixed / ANGLE_TO_INDEX_SCALE) % ANGLE_MAX
        return cosTable[index]
    }
}
