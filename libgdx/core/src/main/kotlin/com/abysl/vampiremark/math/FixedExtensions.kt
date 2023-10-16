package com.abysl.vampiremark.math

fun Int.toFixed(): FixedPoint {
    return FixedPoint(this shl FixedPoint.FIXED_POINT_SHIFT)
}
