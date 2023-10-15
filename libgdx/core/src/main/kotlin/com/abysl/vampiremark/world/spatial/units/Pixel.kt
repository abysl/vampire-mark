package com.abysl.vampiremark.world.spatial.units

import com.abysl.vampiremark.world.spatial.SpatialConfig
import kotlin.math.absoluteValue

data class Pixel(private val value: Int) {
    val toChunk by lazy { Chunk(value / (SpatialConfig.TILE_SIZE * SpatialConfig.CHUNK_SIZE))}

    val toFloat: Float by lazy { value.toFloat() }
    val toInt: Int by lazy { value }

    fun abs(): Pixel = Pixel(value.absoluteValue)

    operator fun plus(other: Pixel) = Pixel(this.value + other.value)
    operator fun minus(other: Pixel) = Pixel(this.value - other.value)
    operator fun times(other: Pixel) = Pixel(this.value * other.value)
    operator fun div(other: Pixel) = Pixel(this.value / other.value)

    operator fun plus(other: Int) = Pixel(this.value + other)
    operator fun minus(other: Int) = Pixel(this.value - other)
    operator fun times(other: Int) = Pixel(this.value * other)
    operator fun div(other: Int) = Pixel(this.value / other)
}
