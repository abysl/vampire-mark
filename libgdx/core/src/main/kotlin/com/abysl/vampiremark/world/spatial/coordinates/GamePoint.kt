package com.abysl.vampiremark.world.spatial.coordinates

import com.abysl.vampiremark.world.spatial.units.Layer

data class GamePoint(
    val chunkPoint: ChunkPoint,
    val pixelPoint: PixelPoint,
    val layer: Layer
) {
    val globalPoint: PixelPoint by lazy {
        (chunkPoint.toPixelPoint + pixelPoint).layer(layer)
    }
}
