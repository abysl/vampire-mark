package com.abysl.vampiremark.world.spatial.units

import com.abysl.vampiremark.world.spatial.SpatialConfig

data class Chunk(val value: Int) {
    operator fun plus(other: Chunk) = Chunk(this.value + other.value)
    operator fun minus(other: Chunk) = Chunk(this.value - other.value)
    operator fun times(factor: Int) = Chunk(this.value * factor)
    operator fun div(factor: Int) = Chunk(this.value / factor)


    val toPixel by lazy { Pixel(value * SpatialConfig.TILE_SIZE * SpatialConfig.CHUNK_SIZE) }
}
