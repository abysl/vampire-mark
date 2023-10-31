package com.abysl.vampiremark.world.spatial.coordinates

import com.abysl.vampiremark.world.spatial.units.*
import com.badlogic.gdx.math.Vector2
import kotlinx.serialization.Serializable

@Serializable
sealed class Coordinate(val x: SpatialUnit, val y: SpatialUnit, val z: Layer) {
    fun toVector2(): Vector2 = Vector2(x.toUnitFloat(), y.toUnitFloat())

    operator fun plus(other: Coordinate): PixelCoord {
        return PixelCoord(
            this.x + other.x,
            this.y + other.y,
            this.z
        )
    }

    operator fun minus(other: Coordinate): PixelCoord {
        return PixelCoord(
            this.x - other.x,
            this.y - other.y,
            this.z
        )
    }

    operator fun times(factor: Int): PixelCoord {
        return PixelCoord(
            this.x * factor,
            this.y * factor,
            this.z
        )
    }

    operator fun div(factor: Int): PixelCoord {
        return PixelCoord(
            this.x / factor,
            this.y / factor,
            this.z
        )
    }

    operator fun rem(factor: Int): PixelCoord {
        return PixelCoord(
            this.x % factor,
            this.y % factor,
            this.z
        )
    }

    operator fun unaryMinus(): PixelCoord {
        return PixelCoord(
            -this.x,
            -this.y,
            this.z
        )
    }
}

@Serializable
data class PixelCoord(val xPixel: Pixel, val yPixel: Pixel, val zLayer: Layer) : Coordinate(xPixel, yPixel, zLayer) {
    fun toTileCoord(): TileCoord {
        return TileCoord(
            xPixel.toTile(),
            yPixel.toTile(),
            zLayer
        )
    }

    fun toChunkCoord(): ChunkCoord {
        return ChunkCoord(
            xPixel.toChunk(),
            yPixel.toChunk(),
            zLayer
        )
    }
}

@Serializable
data class TileCoord(val xTile: Tile, val yTile: Tile, val zLayer: Layer) : Coordinate(xTile, yTile, zLayer) {
    fun toPixelCoord(): PixelCoord {
        return PixelCoord(
            xTile.toPixel(),
            yTile.toPixel(),
            zLayer
        )
    }

    fun toChunkCoord(): ChunkCoord {
        return ChunkCoord(
            xTile.toChunk(),
            yTile.toChunk(),
            zLayer
        )
    }
}

@Serializable
data class ChunkCoord(val xChunk: Chunk, val yChunk: Chunk, val zLayer: Layer) : Coordinate(xChunk, yChunk, zLayer) {
    fun toPixelCoord(): PixelCoord {
        return PixelCoord(
            xChunk.toPixel(),
            yChunk.toPixel(),
            zLayer
        )
    }

    fun toTileCoord(): TileCoord {
        return TileCoord(
            xChunk.toTile(),
            yChunk.toTile(),
            zLayer
        )
    }
}

