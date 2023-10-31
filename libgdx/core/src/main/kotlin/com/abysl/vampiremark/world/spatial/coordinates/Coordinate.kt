package com.abysl.vampiremark.world.spatial.coordinates

import com.abysl.vampiremark.world.spatial.SpatialConfig
import com.abysl.vampiremark.world.spatial.units.*
import com.badlogic.gdx.math.Vector2
import kotlinx.serialization.Serializable

@Serializable
sealed class Coordinate(val x: SpatialUnit, val y: SpatialUnit, val z: Layer) {
    fun toVector2(): Vector2 = Vector2(x.toUnitFloat(), y.toUnitFloat())

    operator fun plus(other: Coordinate): PixelCoordinate {
        return PixelCoordinate(
            this.x + other.x,
            this.y + other.y,
            this.z
        )
    }

    operator fun minus(other: Coordinate): PixelCoordinate {
        return PixelCoordinate(
            this.x - other.x,
            this.y - other.y,
            this.z
        )
    }

    operator fun times(factor: Int): PixelCoordinate {
        return PixelCoordinate(
            this.x * factor,
            this.y * factor,
            this.z
        )
    }

    operator fun div(factor: Int): PixelCoordinate {
        return PixelCoordinate(
            this.x / factor,
            this.y / factor,
            this.z
        )
    }

    operator fun rem(factor: Int): PixelCoordinate {
        return PixelCoordinate(
            this.x % factor,
            this.y % factor,
            this.z
        )
    }

    operator fun unaryMinus(): PixelCoordinate {
        return PixelCoordinate(
            -this.x,
            -this.y,
            this.z
        )
    }
}

@Serializable
data class PixelCoordinate(val xPixel: Pixel, val yPixel: Pixel, val zLayer: Layer) : Coordinate(xPixel, yPixel, zLayer) {
    fun toTileCoordinate(): TileCoordinate {
        return TileCoordinate(
            xPixel.toTile(),
            yPixel.toTile(),
            zLayer
        )
    }

    fun toChunkCoordinate(): ChunkCoordinate {
        return ChunkCoordinate(
            xPixel.toChunk(),
            yPixel.toChunk(),
            zLayer
        )
    }
}

@Serializable
data class TileCoordinate(val xTile: Tile, val yTile: Tile, val zLayer: Layer) : Coordinate(xTile, yTile, zLayer) {
    fun toPixelCoordinate(): PixelCoordinate {
        return PixelCoordinate(
            xTile.toPixel(),
            yTile.toPixel(),
            zLayer
        )
    }

    fun toChunkCoordinate(): ChunkCoordinate {
        return ChunkCoordinate(
            xTile.toChunk(),
            yTile.toChunk(),
            zLayer
        )
    }

    fun toLocalTileCoordinate(): TileCoordinate = (this % SpatialConfig.CHUNK_SIZE).toTileCoordinate()
    fun toGlobalCoordinate(chunkCoordinate: ChunkCoordinate): TileCoordinate = (this + chunkCoordinate).toTileCoordinate()
}

@Serializable
data class ChunkCoordinate(val xChunk: Chunk, val yChunk: Chunk, val zLayer: Layer) : Coordinate(xChunk, yChunk, zLayer) {
    fun toPixelCoordinate(): PixelCoordinate {
        return PixelCoordinate(
            xChunk.toPixel(),
            yChunk.toPixel(),
            zLayer
        )
    }

    fun toTileCoordinate(): TileCoordinate {
        return TileCoordinate(
            xChunk.toTile(),
            yChunk.toTile(),
            zLayer
        )
    }
}

