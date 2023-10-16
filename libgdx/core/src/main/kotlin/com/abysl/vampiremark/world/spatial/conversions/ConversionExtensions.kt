package com.abysl.vampiremark.world.spatial.conversions

import com.abysl.vampiremark.world.spatial.SpatialConfig
import kotlin.math.roundToInt

val Int.tile: Int
    get() = this * SpatialConfig.TILE_SIZE

val Double.tile: Int
    get() = (this * SpatialConfig.TILE_SIZE).toInt()

val Int.chunk: Int
    get() = this * SpatialConfig.TILE_SIZE * SpatialConfig.CHUNK_SIZE

val Double.chunk: Int
    get() = (this * SpatialConfig.TILE_SIZE * SpatialConfig.CHUNK_SIZE).toInt()


