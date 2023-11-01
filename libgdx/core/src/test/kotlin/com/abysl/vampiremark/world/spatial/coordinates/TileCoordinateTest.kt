package com.abysl.vampiremark.world.spatial.coordinates

import com.abysl.vampiremark.world.spatial.SpatialConfig
import com.abysl.vampiremark.world.spatial.units.Chunk
import com.abysl.vampiremark.world.spatial.units.Layer
import com.abysl.vampiremark.world.spatial.units.Pixel
import com.abysl.vampiremark.world.spatial.units.Tile
import com.abysl.vampiremark.world.spatial.units.UnitExtensions.layer
import com.abysl.vampiremark.world.spatial.units.UnitExtensions.tile
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TileCoordinateTest {

    @Test
    fun toPixelCoordinate() {
        val tileCoordinate = TileCoordinate(Tile(10), Tile(20), Layer(1))
        val expectedPixelCoordinate = PixelCoordinate(Pixel(10 * SpatialConfig.TILE_SIZE), Pixel(20 * SpatialConfig.TILE_SIZE), Layer(1))

        assertEquals(expectedPixelCoordinate, tileCoordinate.toPixelCoordinate())
    }

    @Test
    fun toChunkCoordinate() {
        val tileCoordinate = TileCoordinate(Tile(10), Tile(20), Layer(1))
        val expectedChunkCoordinate = ChunkCoordinate(
            Chunk(10 / SpatialConfig.CHUNK_SIZE),
            Chunk(20 / SpatialConfig.CHUNK_SIZE),
            Layer(1)
        )

        assertEquals(expectedChunkCoordinate, tileCoordinate.toChunkCoordinate())
    }

    @Test
    fun toLocalTileCoordinate() {
        val tileCoordinate = TileCoordinate(10.tile, 20.tile, 1.layer)
        val expectedLocalTileCoordinate = TileCoordinate(
            (10 % SpatialConfig.CHUNK_SIZE).tile,
            (20 % SpatialConfig.CHUNK_SIZE).tile,
            1.layer
        )

        assertEquals(expectedLocalTileCoordinate, tileCoordinate.toLocalTileCoordinate())
    }

    @Test
    fun toGlobalCoordinate() {
        val tileCoordinate = TileCoordinate(Tile(10), Tile(20), Layer(1))
        val chunkCoordinate = ChunkCoordinate(Chunk(1), Chunk(2), Layer(1))

        val expectedGlobalCoordinate = TileCoordinate(
            Tile(10 + SpatialConfig.CHUNK_SIZE),
            Tile(20 + 2 * SpatialConfig.CHUNK_SIZE),
            Layer(1)
        )

        assertEquals(expectedGlobalCoordinate, tileCoordinate.toGlobalCoordinate(chunkCoordinate))
    }
}
