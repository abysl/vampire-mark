package com.abysl.vampiremark.world.tiles

import com.abysl.vampiremark.world.spatial.coordinates.ChunkCoordinate
import com.abysl.vampiremark.world.spatial.coordinates.TileCoordinate

class TileMapChunk(val coord: ChunkCoordinate, val tileStacks: MutableMap<TileCoordinate, TileStack> = mutableMapOf()) {
    fun getTileStack(coord: TileCoordinate): TileStack? = tileStacks[coord]
    fun setTileStack(coord: TileCoordinate, tileStack: TileStack) { tileStacks[coord] = tileStack }
}
