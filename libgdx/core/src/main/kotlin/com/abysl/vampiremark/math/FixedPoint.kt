package com.abysl.vampiremark.math

class FixedPoint(val rawValue: Int): Comparable<FixedPoint> {
    fun abs(): FixedPoint {
        return FixedPoint(kotlin.math.abs(rawValue))
    }

    fun inv(): FixedPoint {
        return FixedPoint(rawValue.inv())
    }

    infix fun shr(amount: Int): FixedPoint {
        return FixedPoint(rawValue shr amount)
    }

    infix fun shl(amount: Int): FixedPoint {
        return FixedPoint(rawValue shl amount)
    }

    infix fun and(other: FixedPoint): FixedPoint {
        return FixedPoint(rawValue and other.rawValue)
    }

    infix fun or(other: FixedPoint): FixedPoint {
        return FixedPoint(rawValue or other.rawValue)
    }

    infix fun xor(other: FixedPoint): FixedPoint {
        return FixedPoint(rawValue xor other.rawValue)
    }

    operator fun unaryMinus(): FixedPoint {
        return FixedPoint(-rawValue)
    }

    operator fun plus(other: FixedPoint): FixedPoint {
        return fromRawValue(this.rawValue + other.rawValue)
    }

    operator fun plus(other: Int): FixedPoint {
        return this + other.toFixed()
    }

    operator fun minus(other: FixedPoint): FixedPoint {
        return fromRawValue(this.rawValue - other.rawValue)
    }

    operator fun minus(other: Int): FixedPoint {
        return this - other.toFixed()
    }

    operator fun times(other: FixedPoint): FixedPoint {
        return fromRawValue((this.rawValue.toLong() * other.rawValue shr FIXED_POINT_SHIFT).toInt())
    }

    operator fun times(other: Int): FixedPoint {
        return fromRawValue(this.rawValue * other)
    }

    operator fun div(other: FixedPoint): FixedPoint {
        return fromRawValue(((this.rawValue.toLong() shl FIXED_POINT_SHIFT) / other.rawValue).toInt())
    }

    operator fun div(other: Int): FixedPoint {
        return fromRawValue(this.rawValue / other)
    }

    fun toInt(): Int {
        return rawValue shr FIXED_POINT_SHIFT
    }

    fun toDouble(): Double {
        return rawValue.toDouble() / FIXED_POINT_FACTOR
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FixedPoint) return false
        return rawValue == other.rawValue
    }

    override fun hashCode(): Int {
        return rawValue
    }

    override fun compareTo(other: FixedPoint): Int {
        return this.rawValue.compareTo(other.rawValue)
    }

    override fun toString(): String {
        return (rawValue.toDouble() / FIXED_POINT_FACTOR).toString()
    }

    companion object {
        const val FIXED_POINT_SHIFT = 16
        const val FIXED_POINT_FACTOR = 1 shl FIXED_POINT_SHIFT

        fun fromInt(value: Int): FixedPoint {
            return FixedPoint(value shl FIXED_POINT_SHIFT)
        }

        fun fromDouble(value: Double): FixedPoint {
            return FixedPoint((value * FIXED_POINT_FACTOR).toInt())
        }

        fun fromRawValue(rawValue: Int): FixedPoint {
            return FixedPoint(rawValue)
        }
    }
}


