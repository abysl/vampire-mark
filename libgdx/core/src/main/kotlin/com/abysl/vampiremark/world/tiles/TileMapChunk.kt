package com.abysl.vampiremark.world.tiles

import com.abysl.vampiremark.world.spatial.coordinates.ChunkCoordinate
import com.abysl.vampiremark.world.spatial.coordinates.TileCoordinate

class TileMapChunk(
    val coordinate: ChunkCoordinate,
    val tileStacks: MutableMap<TileCoordinate, TileStack> = mutableMapOf()
) : Iterable<Map.Entry<TileCoordinate, TileStack>> {

    fun getTileStack(coord: TileCoordinate): TileStack? = tileStacks[coord]

    fun setTileStack(coord: TileCoordinate, tileStack: TileStack) {
        tileStacks[coord] = tileStack
    }

    override fun iterator(): Iterator<Map.Entry<TileCoordinate, TileStack>> {
        return tileStacks.iterator()
    }
}
