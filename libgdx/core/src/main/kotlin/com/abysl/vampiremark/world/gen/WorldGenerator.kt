package com.abysl.vampiremark.world.gen

import com.abysl.vampiremark.world.spatial.coordinates.ChunkCoord
import com.abysl.vampiremark.world.spatial.units.Chunk
import com.abysl.vampiremark.world.tiles.TileMapChunk

interface WorldGenerator {

    /**
     * Generate the tiles for a specific chunk based on its coordinates.
     *
     * @param chunkCoord The coordinates of the chunk to be generated.
     * @param tileMap The tile map to which the generated chunk will belong.
     * @return The generated chunk filled with tiles.
     */
    fun generate(chunkCoord: ChunkCoord, tileMap: TileMapChunk): Chunk
}

