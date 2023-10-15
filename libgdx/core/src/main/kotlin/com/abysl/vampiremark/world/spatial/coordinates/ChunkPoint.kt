package com.abysl.vampiremark.world.spatial.coordinates

import com.abysl.vampiremark.world.spatial.conversions.layer
import com.abysl.vampiremark.world.spatial.units.Chunk

data class ChunkPoint(
    val x: Chunk,
    val y: Chunk
){
    val toPixelPoint by lazy { PixelPoint(x.toPixel, y.toPixel, 0.layer) }
}
