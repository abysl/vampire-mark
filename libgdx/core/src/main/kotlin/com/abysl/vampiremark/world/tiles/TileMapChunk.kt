package com.abysl.vampiremark.world.tiles

import com.abysl.vampiremark.world.spatial.coordinates.ChunkCoord
import com.abysl.vampiremark.world.spatial.coordinates.TileCoord

class TileMapChunk(val coord: ChunkCoord, val tileStacks: MutableMap<TileCoord, TileStack> = mutableMapOf()) {
    fun getTileStack(coord: TileCoord): TileStack? = tileStacks[coord]
    fun setTileStack(coord: TileCoord, tileStack: TileStack) { tileStacks[coord] = tileStack }
}
