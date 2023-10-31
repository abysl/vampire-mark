package com.abysl.vampiremark.world.tiles

import com.abysl.vampiremark.world.gen.WorldGenerator
import com.abysl.vampiremark.world.spatial.coordinates.ChunkCoordinate
import com.abysl.vampiremark.world.spatial.coordinates.TileCoordinate

class ChunkedTileMap(val worldGenerator: WorldGenerator) : TileMap {

    private val chunks: MutableMap<ChunkCoordinate, TileMapChunk> = mutableMapOf()

    override fun getTileStack(coord: TileCoordinate): TileStack? {
        val chunkCoordinate = coord.toChunkCoordinate()
        val localCoordinate = coord.toLocalTileCoordinate()
        if (chunks[chunkCoordinate] == null) {
            chunks[chunkCoordinate] = worldGenerator.generateChunk(chunkCoordinate)
        }
        return chunks[chunkCoordinate]?.getTileStack(localCoordinate)
    }

    override fun setTileStack(globalCoordinate: TileCoordinate, tileStack: TileStack) {
        val chunkCoordinate = globalCoordinate.toChunkCoordinate()
        val localCoordinate = globalCoordinate.toLocalTileCoordinate()
        val chunk = chunks.getOrPut(chunkCoordinate) { TileMapChunk(chunkCoordinate) }
        chunk.setTileStack(localCoordinate, tileStack)
    }

    override fun getTileStacks(tileCoordinates: List<TileCoordinate>): Map<TileCoordinate, TileStack> {
        val result = mutableMapOf<TileCoordinate, TileStack>()
        tileCoordinates.forEach { coord ->
            val tileStack = getTileStack(coord)
            if (tileStack != null) {
                result[coord] = tileStack
            }
        }
        return result
    }

    override fun setTileStacks(tileStacks: Map<TileCoordinate, TileStack>) {
        tileStacks.forEach { (coord, tileStack) ->
            setTileStack(coord, tileStack)
        }
    }

    override fun getChunk(chunkCoordinate: ChunkCoordinate): TileMapChunk {
        return chunks.getOrPut(chunkCoordinate) {
            // If the chunk isn't already loaded, generate it using the WorldGenerator
            worldGenerator.generateChunk(chunkCoordinate)
        }
    }

    override fun setChunk(chunkCoordinate: ChunkCoordinate, chunk: TileMapChunk) {
        // Store the given chunk in the chunks map
        chunks[chunkCoordinate] = chunk
    }
}
