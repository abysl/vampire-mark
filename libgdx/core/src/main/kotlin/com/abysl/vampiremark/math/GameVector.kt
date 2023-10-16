package com.abysl.vampiremark.math

import kotlin.math.PI

class GameVector(
    private val initX: Int = 0,
    private val initY: Int = 0,
    var layer: Byte = 0
) {
    var xFixed = initX * FIXED_POINT_FACTOR
    var yFixed = initY * FIXED_POINT_FACTOR

    companion object {
        private const val FIXED_POINT_SHIFT = 16
        private const val FIXED_POINT_FACTOR = 1 shl FIXED_POINT_SHIFT
    }

    var x: Int
        get() = xFixed / FIXED_POINT_FACTOR
        set(value) {
            xFixed = value * FIXED_POINT_FACTOR
        }

    var y: Int
        get() = yFixed / FIXED_POINT_FACTOR
        set(value) {
            yFixed = value * FIXED_POINT_FACTOR
        }


    fun set(x: Int, y: Int, layer: Byte = 0): GameVector {
        this.x = x
        this.y = y
        this.layer = layer
        return this
    }

    operator fun plusAssign(other: GameVector) {
        xFixed += other.xFixed
        yFixed += other.yFixed
    }

    operator fun minusAssign(other: GameVector) {
        xFixed -= other.xFixed
        yFixed -= other.yFixed
    }

    fun dot(other: GameVector): Int {
        val dotFixed = (xFixed * other.xFixed + yFixed * other.yFixed) shr FIXED_POINT_SHIFT
        return dotFixed
    }

    fun magnitude(): Double {
        val magSquaredFixed = ((xFixed.toLong() * xFixed + yFixed.toLong() * yFixed) shr FIXED_POINT_SHIFT).toInt()
        return kotlin.math.sqrt(magSquaredFixed.toDouble()) / FIXED_POINT_FACTOR
    }

    fun scale(scalar: Int): GameVector {
        xFixed = (xFixed * scalar) shr FIXED_POINT_SHIFT
        yFixed = (yFixed * scalar) shr FIXED_POINT_SHIFT
        return this
    }

    fun normalize(): GameVector {
        val mag = magnitude()
        if (mag > 0) {
            x = ((xFixed / mag) * FIXED_POINT_FACTOR).toInt() shr FIXED_POINT_SHIFT
            y = ((yFixed / mag) * FIXED_POINT_FACTOR).toInt() shr FIXED_POINT_SHIFT
        }
        return this
    }

    fun normalizeAndScale(scale: Int): GameVector {
        normalize()
        scale(scale)
        return this
    }

    fun distanceTo(other: GameVector): Double {
        val dx = (other.xFixed - xFixed).toDouble() / FIXED_POINT_FACTOR
        val dy = (other.yFixed - yFixed).toDouble() / FIXED_POINT_FACTOR
        return kotlin.math.sqrt(dx * dx + dy * dy)
    }

    fun angleTo(other: GameVector): Double {
        val dotProduct = dot(other)
        val magProduct = (magnitude() * other.magnitude()).toInt()
        return kotlin.math.acos((dotProduct / magProduct.toDouble()) * FIXED_POINT_FACTOR)
    }

    fun rotate(angleDegrees: Int): GameVector {
        val cosAngle = FixedPointMath.cos(angleDegrees)
        val sinAngle = FixedPointMath.sin(angleDegrees)
        val newXFixed = ((xFixed * cosAngle - yFixed * sinAngle) shr FIXED_POINT_SHIFT)
        val newYFixed = ((xFixed * sinAngle + yFixed * cosAngle) shr FIXED_POINT_SHIFT)
        xFixed = newXFixed
        yFixed = newYFixed
        return this
    }

    fun lerp(target: GameVector, alpha: Float): GameVector {
        val invAlpha = 1.0f - alpha
        x = ((xFixed * invAlpha + target.xFixed * alpha) * FIXED_POINT_FACTOR).toInt() shr FIXED_POINT_SHIFT
        y = ((yFixed * invAlpha + target.yFixed * alpha) * FIXED_POINT_FACTOR).toInt() shr FIXED_POINT_SHIFT
        return this
    }

    fun equals(other: GameVector, tolerance: Int): Boolean {
        val toleranceFixed = tolerance shl FIXED_POINT_SHIFT
        return kotlin.math.abs(xFixed - other.xFixed) <= toleranceFixed &&
            kotlin.math.abs(yFixed - other.yFixed) <= toleranceFixed
    }

    override fun toString(): String {
        return "MutableGameVector(x=$x, y=$y, layer=$layer)"
    }
}


