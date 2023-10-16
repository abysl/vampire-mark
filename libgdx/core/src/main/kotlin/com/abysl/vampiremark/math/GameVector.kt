package com.abysl.vampiremark.math

class GameVector(
    initX: Int = 0,
    initY: Int = 0,
    var layer: Byte = 0
) {

    var xFixed: FixedPoint = FixedPoint.fromInt(initX)
    var yFixed: FixedPoint = FixedPoint.fromInt(initY)

    var x: Int
        get() = xFixed.toInt()
        set(value) {
            xFixed = FixedPoint.fromInt(value)
        }

    var y: Int
        get() = yFixed.toInt()
        set(value) {
            yFixed = FixedPoint.fromInt(value)
        }

    fun set(x: Int, y: Int, layer: Byte = 0): GameVector {
        this.x = x
        this.y = y
        this.layer = layer
        return this
    }

    fun normalize(): GameVector {
        val mag = magnitude()
        if (mag > FixedPoint.fromInt(0)) {
            xFixed /= mag
            yFixed /= mag
        }
        return this
    }

    fun dot(other: GameVector): FixedPoint {
        return (xFixed * other.xFixed + yFixed * other.yFixed) shr FixedPoint.FIXED_POINT_SHIFT
    }

    fun magnitude(): FixedPoint {
        val magSquaredFixed = (xFixed * xFixed + yFixed * yFixed) shr FixedPoint.FIXED_POINT_SHIFT
        return FixedMath.sqrt(magSquaredFixed)
    }

    fun scale(scalar: Int): GameVector {
        xFixed = (xFixed * FixedPoint.fromInt(scalar)) shr FixedPoint.FIXED_POINT_SHIFT
        yFixed = (yFixed * FixedPoint.fromInt(scalar)) shr FixedPoint.FIXED_POINT_SHIFT
        return this
    }

    fun distanceTo(other: GameVector): FixedPoint {
        val dx = other.xFixed - xFixed
        val dy = other.yFixed - yFixed
        return FixedMath.sqrt((dx * dx + dy * dy) shr FixedPoint.FIXED_POINT_SHIFT)
    }

    fun angleTo(other: GameVector): FixedPoint {
        val dotProduct = dot(other)
        val magProduct = magnitude() * other.magnitude()
        return FixedMath.acos(dotProduct / magProduct)
    }

    fun rotate(angleDegrees: Int): GameVector {
        val cosAngle = FixedMath.cos(angleDegrees)
        val sinAngle = FixedMath.sin(angleDegrees)
        val newXFixed = ((xFixed * cosAngle - yFixed * sinAngle) shr FixedPoint.FIXED_POINT_SHIFT)
        val newYFixed = ((xFixed * sinAngle + yFixed * cosAngle) shr FixedPoint.FIXED_POINT_SHIFT)
        xFixed = newXFixed
        yFixed = newYFixed
        return this
    }

    fun lerp(target: GameVector, alpha: FixedPoint): GameVector {
        val invAlpha = FixedPoint.fromInt(1) - alpha
        xFixed = (xFixed * invAlpha + target.xFixed * alpha)
        yFixed = (yFixed * invAlpha + target.yFixed * alpha)
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

    override fun toString(): String {
        return "MutableGameVector(x=$x, y=$y, layer=$layer)"
    }
}
