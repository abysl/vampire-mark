package com.abysl.vampiremark.world.tiles

import com.abysl.vampiremark.world.spatial.SpatialConfig
import com.abysl.vampiremark.world.spatial.coordinates.ChunkCoord
import com.abysl.vampiremark.world.spatial.coordinates.TileCoord
import com.abysl.vampiremark.world.spatial.units.UnitExtensions.tile

class ChunkedTileMap : TileMap {

    private val chunks: MutableMap<ChunkCoord, TileMapChunk> = mutableMapOf()

    override fun getTileStack(coord: TileCoord): TileStack? {
        val chunkCoord = ChunkCoord(coord.x.toChunk(), coord.y.toChunk(), coord.z)
        val localCoord = TileCoord((coord.x.value % SpatialConfig.CHUNK_SIZE).tile, (coord.y.value % SpatialConfig.CHUNK_SIZE).tile, coord.z)
        return chunks[chunkCoord]?.getTileStack(localCoord)
    }

    override fun setTileStack(coord: TileCoord, tileStack: TileStack) {
        val chunkCoord = ChunkCoord(coord.x.toChunk(), coord.y.toChunk(), coord.z)
        val localCoord = (coord % SpatialConfig.CHUNK_SIZE).toTileCoord()
        val chunk = chunks.getOrPut(chunkCoord) { TileMapChunk(chunkCoord) }
        chunk.setTileStack(localCoord, tileStack)
    }

    override fun getTileStacks(coords: List<TileCoord>): Map<TileCoord, TileStack> {
        val result = mutableMapOf<TileCoord, TileStack>()
        coords.forEach { coord ->
            val tileStack = getTileStack(coord)
            if (tileStack != null) {
                result[coord] = tileStack
            }
        }
        return result
    }

    override fun setTileStacks(tileStacks: Map<TileCoord, TileStack>) {
        tileStacks.forEach { (coord, tileStack) ->
            setTileStack(coord, tileStack)
        }
    }
}
