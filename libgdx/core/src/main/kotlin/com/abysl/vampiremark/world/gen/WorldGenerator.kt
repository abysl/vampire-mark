package com.abysl.vampiremark.world.gen

import com.abysl.vampiremark.world.spatial.coordinates.ChunkCoordinate
import com.abysl.vampiremark.world.spatial.units.Chunk
import com.abysl.vampiremark.world.tiles.ChunkedTileMap
import com.abysl.vampiremark.world.tiles.TileMapChunk

interface WorldGenerator {

    fun generateChunk(chunkCoordinate: ChunkCoordinate): TileMapChunk
}

