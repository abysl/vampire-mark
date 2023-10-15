package com.abysl.vampiremark.world.spatial.conversions

import com.abysl.vampiremark.world.spatial.SpatialConfig
import com.abysl.vampiremark.world.spatial.units.Chunk
import com.abysl.vampiremark.world.spatial.units.Layer
import com.abysl.vampiremark.world.spatial.units.Pixel

val Int.pixel: Pixel
    get() = Pixel(this)

val Int.tile: Pixel
    get() = Pixel(this * SpatialConfig.TILE_SIZE)

val Int.chunk: Chunk
    get() =  Chunk(this)

val Int.layer: Layer
    get() = Layer(this)

val Double.tile: Pixel
    get() = Pixel((this * SpatialConfig.TILE_SIZE).toInt())


